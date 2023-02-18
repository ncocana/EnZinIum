package edu.craptocraft.enzinium;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class TokenContract {
    
    private Map<PublicKey, Double> balances = new HashMap<>();
    private String name = "";
    private String symbol = "";
    private Double totalSupply = 0d;
    private Double tokenPrice = 0d;
    private Address owner = null;
    private PublicKey ownerPK = null;
    private int totalTokensSold = 0;

    public TokenContract(Address owner) {
        this.owner = owner;
        this.ownerPK = owner.getPK();
    }

    public Address getOwner() {
        return this.owner;
    }

    public Map<PublicKey, Double> getBalances() {
        return this.balances;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String symbol() {
        return this.symbol;
    }

    public void setSymbol(String newSymbol) {
        this.symbol = newSymbol;
    }

    public Double totalSupply() {
        return this.totalSupply;
    }

    public void setTotalSupply(double newTotalSupply) {
        this.totalSupply = newTotalSupply;
    }
    
    public Double getTokenPrice() {
        return this.tokenPrice;
    }

    public void setTokenPrice(double newTokenPrice) {
        this.tokenPrice = newTokenPrice;
    }

    // Adds an owner if it isn't already on the HashMap.
    public void addOwner(PublicKey PK, Double unitsSupply) {
        getBalances().putIfAbsent(PK, unitsSupply);
    }

    // Returns the number of elements in the HashMap.
    public int numOwners() {
        return this.getBalances().size();
    }

    // Returns the owner's balance.
    // If there are no mappings for the specified key,
    // returns a default value (0d).
    public Double balanceOf(PublicKey owner) {
        return this.getBalances().getOrDefault(owner, 0d);
    }

    // If "holds" is false, throws an Exception.
    private void require(Boolean holds) throws Exception {
        if (!holds) {
            throw new Exception();
        }
    }

    // Checks if the owner's balance is superior or equal to "unitsSupply".
    // If not, fails silently.
    // It it is, substracts the value of "unitsSupply" out of the owner's account
    // and then adds it to the recipient's account.
    public void transfer(PublicKey recipient, Double unitsSupply) {
        try {
            require(balanceOf(ownerPK) >= unitsSupply);
            this.getBalances().compute(ownerPK, (pk, tokens) -> tokens - unitsSupply);
            this.getBalances().put(recipient, balanceOf(recipient) + unitsSupply);
        } catch (Exception exception) {
            // Nothing happens.
        }
    }

    // Same as above but with the recipient's balance instead of the owner's.
    public void transfer(PublicKey sender, PublicKey recipient, Double unitsSupply) {
        try {
            require(balanceOf(sender) >= unitsSupply);
            this.getBalances().compute(sender, (pk, tokens) -> tokens - unitsSupply);
            this.getBalances().put(recipient, balanceOf(recipient) + unitsSupply);
        } catch (Exception exception) {
            // Nothing happens.
        }
    }

    // Shows a list of the buyers excluding the owner.
    public void owners() {
        for (PublicKey pk : this.getBalances().keySet()) {
            if (!pk.equals(this.ownerPK)) {
                System.out.println("\nPublic Key: " + pk.hashCode() + "\nNúmero de tokens: " + getBalances().get(pk) + " " + symbol());
            }
        }
    }

    // Sums the number of tokens of each PK in "getBalances()",
    // then substracts the number of tokens of the owner,
    // and returns the final result.
    public int totalTokensSold() {
        this.getBalances().forEach((pk, unitsSupply) -> this.totalTokensSold += unitsSupply);
        this.totalTokensSold -= balanceOf(ownerPK);
        return this.totalTokensSold;
    }

    // Checks if the recipients's balance is superior or equal to "ezi" (enziniums).
    // If not, fails silently.
    // It it is, then divides the number of enzinums by the token price,
    // and transfer the units to the recipient and the enziniums to the owner.
    void payable(PublicKey recipient, Double ezi) {
        try {
            require(ezi >= this.getTokenPrice());
            Double unitsSupply = Math.floor(ezi / getTokenPrice());
            transfer(recipient, unitsSupply);
            this.getOwner().transferEZI(ezi);
        } catch (Exception exception) {
            // Nothing happens.
        }
    }

    @Override
    public String toString() {
        return "\nName: " + getName() + "\nSymbol: " + symbol() + "\nTotal Supply: " + totalSupply();
    }

}
