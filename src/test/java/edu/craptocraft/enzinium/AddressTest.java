package edu.craptocraft.enzinium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;

public class AddressTest {
    
    private Address address = null;

    @Before
    public void setup_address() {

        address = new Address();
        assertNotNull(address);
        address.generateKeyPair();

    }
    
    @Test
    public void test_KeysArePresent() {

        assertNotNull(address.getPK());
        assertNotNull(address.isSKpresent());

    }
    
    @Test
    public void test_transferEzi() {

        assertEquals(0d, address.getBalance(), 0d);

        address.transferEZI(20d);
        assertEquals(20d, address.getBalance(), 0d);

        address.transferEZI(20d);
        assertEquals(40d, address.getBalance(), 0d);

    }

}
