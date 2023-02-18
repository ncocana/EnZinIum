package edu.craptocraft.enzinium;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Address {
    
    private PublicKey PK = null;
    private PrivateKey SK = null;
    private double balance = 0.0d;
    private final String symbol = "EZI";

    public Address() {}

    public PublicKey getPK() {
        return this.PK;
    }

    private void setPK (PublicKey newPK) {
        this.PK = newPK;
    }

    private PrivateKey getSK() {
        return this.SK;
    }

    private void setSK(PrivateKey newSK) {
        this.SK = newSK;
    }

    private double getBalance() {
        return this.balance;
    }

    private String getSymbol() {
        return this.symbol;
    }

    public void generateKeyPair() {
        KeyPair kPair = GenSig.generateKeyPair();
        this.setPK(kPair.getPublic());
        this.setSK(kPair.getPrivate());
    }

    @Override
    public String toString() {
        return "\nPublick Key: " + getPK().hashCode() + "\nBalance: " + getBalance() + getSymbol();
    }

}
