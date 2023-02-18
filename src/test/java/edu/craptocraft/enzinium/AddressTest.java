package edu.craptocraft.enzinium;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AddressTest {
    
    @Test
    public void test_SkAndPkArePresent() {

        Address address = new Address();
        assertNotNull(address);
        address.generateKeyPair();
        assertNotNull(address.getPK());
        assertNotNull(address.isSKpresent());

    }

}
