package com.bank.model.accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.cash.Cash;

public abstract class Account {
    protected final Integer customerId;
    protected final String customerName;
    protected Double previousTransaction;
	protected Cash extractionCommission;
	protected String country;
	protected List<Cash> debt;
    protected List<Cash> balances;

    public Account(Integer id, String name, Cash extractionCommission, String country) {
        this.customerId = id;
        this.customerName = name;
        this.balances = new ArrayList<>();
        this.previousTransaction = null;
        this.extractionCommission = extractionCommission;
        this.country = country;
        this.debt = new ArrayList<>();
    }

    public String getCountry() {
		return country;
	}

    public void increaseDebt(Cash cash) {
    	this.debt.add(cash); 
    }
    
	public void setCountry(String country) {
		this.country = country;
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
   
	public void setPreviousTransaction(Double previousTransaction) {
		this.previousTransaction = previousTransaction;
	}

	public Cash getExtractionCommission() {
		return extractionCommission;
	}

	public void setExtractionCommission(Cash extractionCommission) {
		this.extractionCommission = extractionCommission;
	}

	public List<Cash> getDebt() {
		return debt;
	}

	public void setDebt(List<Cash> debt) {
		this.debt = debt;
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
        this.balances.add(cash);
        previousTransaction = cash.getValue();
    }
    
    public void withdraw(Cash cash) {
    	try {
    		if (!accountHasNotEnoughBalance(cash)) {
    	    	Cash balance = balances.stream().filter(current -> isRequiredCash(cash, current)).findFirst().get(); 
        		balance.setValue(balance.getValue() - this.costOfextraction(cash));
        		previousTransaction = cash.getValue()*-1;
    		}
    	}catch(NoSuchElementException exception){
    		throw new NotEnoughBalance();
    	}

    }
    
    /**
     * Returns true if received amount is bigger that account's balance.
     *
     * @return true if received amount is bigger that account's balance.
     */
    public boolean accountHasNotEnoughBalance(Cash cash) { 
    		return this.searchRequiredCash(cash).getValue() < this.costOfextraction(cash);
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
    
    public Double costOfextraction(Cash cash) {
    	return cash.getValue() + this.extractionCommission.getValue();
    }
}
