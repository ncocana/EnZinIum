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

    // Method for test.
    boolean isSKpresent() {
        return this.getSK() != null;
    }

    private void setSK(PrivateKey newSK) {
        this.SK = newSK;
    }

    public double getBalance() {
        return this.balance;
    }

    private String getSymbol() {
        return this.symbol;
    }

    // Generates a public and private key.
    public void generateKeyPair() {
        KeyPair kPair = GenSig.generateKeyPair();
        this.setPK(kPair.getPublic());
        this.setSK(kPair.getPrivate());
    }

    // Transfer the "ezi" (enziniums).
    public void transferEZI(Double ezi) {
        this.balance += ezi;
    }

    // If the "balance" is equal or superior to "ezi" (enziniums),
    // then executes the method "payable()" of the "TokenContract" class,
    // and substracts the amount of enziniums payed out of the "balance".
    public void send(TokenContract contract, Double ezi) {
        if (ezi <= getBalance()) {
            contract.payable(getPK(), ezi);
            this.balance -= ezi;
        }
    }

    @Override
    public String toString() {
        return "\nPublick Key: " + getPK().hashCode() + "\nBalance: " + getBalance() + " " + getSymbol();
    }

}
