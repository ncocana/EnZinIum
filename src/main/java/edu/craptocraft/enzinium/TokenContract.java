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

    public TokenContract(Address owner) {
        this.owner = owner;
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

    public String getSymbol() {
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

    public void addOwner(PublicKey PK, Double unitsSupply) {
        getBalances().putIfAbsent(PK, unitsSupply);
    }

    @Override
    public String toString() {
        return "\nName: " + getName() + "\nSymbol: " + getSymbol() + "\nTotal Supply: " + totalSupply();
    }

}
