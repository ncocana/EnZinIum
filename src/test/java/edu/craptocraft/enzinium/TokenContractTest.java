package edu.craptocraft.enzinium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class TokenContractTest {

    private Address ownerOne = null;
    private Address ownerTwo = null;
    private Address ownerThree = null;
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

        ownerThree = new Address();
        ownerThree.generateKeyPair();

        contract.setTokenPrice(5d);

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

        // There are not enough units. Fails silently.
        contract.transfer(ownerTwo.getPK(), 500d);
        assertEquals(98d, contract.balanceOf(ownerOne.getPK()), 0d);
        assertEquals(52d, contract.balanceOf(ownerTwo.getPK()), 0d);

        // Resale.
        contract.transfer(ownerTwo.getPK(), ownerThree.getPK(), 1d);
        assertEquals(98d, contract.balanceOf(ownerOne.getPK()), 0d);
        assertEquals(51d, contract.balanceOf(ownerTwo.getPK()), 0d);
        assertEquals(1d, contract.balanceOf(ownerThree.getPK()), 0d);

    }
    
    // Verifies that the payable transference did go well.
    @Test
    public void test_payable() {

        ownerThree.transferEZI(20d);

        // OwnerThree buy X number of tickets with all EZI of their account (20).
        contract.payable(ownerThree.getPK(), ownerThree.getBalance());
        // Because the tickets cost 5 EZI, OwnerThree gets 4 tickets.
        assertEquals(4d, contract.balanceOf(ownerThree.getPK()), 0d);
        // Owner's account gets the amount of EZI payed (20).
        assertEquals(20d, contract.getOwner().getBalance(), 0d);

        // Not enough EZIs to buy a ticket.
        contract.payable(ownerThree.getPK(), 4d);
        assertEquals(4d, contract.balanceOf(ownerThree.getPK()), 0d);
        assertEquals(20d, contract.getOwner().getBalance(), 0d);

        // Attempts to buy one and a half ticket.
        // OwnerThree gets only one ticket,
        // and Owner's account gets all the EZI.
        contract.payable(ownerThree.getPK(), 8d);
        assertEquals(5d, contract.balanceOf(ownerThree.getPK()), 0d);
        assertEquals(28d, contract.getOwner().getBalance(), 0d);

    }

}
