package edu.craptocraft.enzinium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class TokenContractTest {

    private Address ownerOne = null;
    private Address ownerTwo = null;
    private TokenContract contract = null;
    
    @Before
    public void setup_addressAndContract() {

        ownerOne = new Address();
        ownerOne.generateKeyPair();
        contract = new TokenContract(ownerOne);
        contract.addOwner(ownerOne.getPK(), 100d);

        assertEquals(1, contract.getBalances().size());

        ownerTwo = new Address();
        ownerTwo.generateKeyPair();
        contract.addOwner(ownerTwo.getPK(), 50d);
        assertEquals(2, contract.getBalances().size());

    }
    
    // Verifies that the owner's balance is correct.
    @Test
    public void test_ownerBalance() {

        assertEquals(100, contract.getBalances().get(ownerOne.getPK()), 0d);
        assertEquals(50, contract.getBalances().get(ownerTwo.getPK()), 0d);

    }
    
    // Verifies that the owner's balance isn't updated if it already exists.
    @Test
    public void test_addSameOwner() {

        assertEquals(100, contract.getBalances().get(ownerOne.getPK()), 0d);
        contract.addOwner(ownerOne.getPK(), 60d);
        assertEquals(100, contract.getBalances().get(ownerOne.getPK()), 0d);

    }
    
    // Verifies that the transference did go well.
    @Test
    public void test_transferUnits() {

        contract.transfer(ownerTwo.getPK(), 2d);
        assertEquals(98d, contract.balanceOf(ownerOne.getPK()), 0d);
        assertEquals(52d, contract.balanceOf(ownerTwo.getPK()), 0d);

    }

}
