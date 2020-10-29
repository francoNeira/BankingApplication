package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final Integer customerId;
    private final String customerName;
    private Double previousTransaction;
    private List<Cash> balances;

    public Account(Integer id, String name) {
        this.customerId = id;
        this.customerName = name;
        this.balances = new ArrayList<>();
        this.previousTransaction = null;
    }

    public Integer customerId() {
        return customerId;
    }
    public String customerName() {
        return customerName;
    }
    public List<Cash> balances() {
        return balances;
    }
    public Double previousTransaction() {
        return previousTransaction;
    }

    /**
     * Returns true if this account has customer id equals to received id.
     *
     * @return true if this account has customer id equals to received id.
     */
    public boolean hasId(Integer id) {
        return customerId.equals(id);
    }

    /**
     * Increases balance and updates previous transaction.
     */
    public void deposit(Cash cash) {
    	if(balancesInclude(cash)) {
    		Cash balance = balances.stream().filter(current -> isRequiredCash(cash, current)).findFirst().get(); 
    		balance.setValue(balance.getValue() + cash.getValue());
    	}
        balances.add(cash);
        previousTransaction = cash.getValue();
    }
    
    public boolean isRequiredCash(Cash cash, Cash balance) {
    	return balance.getCountry() == cash.getCountry() && balance.getClass() == cash.getClass();
    }
    
    public boolean balancesInclude(Cash cash) {
    	return balances.stream().anyMatch(balance -> isRequiredCash(cash, balance));
    }
    
    public Cash searchRequiredCash(Cash cash) {
    	return balances.stream().filter(current -> isRequiredCash(cash, current)).findFirst().get(); 
    }
    	

    /**
     * Decreases balance and updates previous transaction.
     */
    public void withdraw(Cash cash) {
    	Cash balance = balances.stream().filter(current -> isRequiredCash(cash, current)).findFirst().get(); 
    	balance.setValue(balance.getValue() - cash.getValue());
        previousTransaction = cash.getValue()*-1;
    }
}

