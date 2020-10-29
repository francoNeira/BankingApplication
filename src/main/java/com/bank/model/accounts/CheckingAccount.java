package com.bank.model.accounts;

import com.bank.model.cash.Cash;
import com.bank.model.cash.Check;

public class CheckingAccount extends Account {
	
	private Cash overdraftAmount;
	
	private int numberOfOverdrafts;

	public CheckingAccount(Integer id, String name, Cash extractionCommission,
			Cash overdrafts, int amountOfOverdrafts, String country) {
		super(id, name, extractionCommission, country);
		this.overdraftAmount = overdrafts;
		this.numberOfOverdrafts = amountOfOverdrafts;
	}

	public Cash getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(Cash overdrafts) {
		this.overdraftAmount = overdrafts;
	}

	public int getNumberOfOverdrafts() {
		return numberOfOverdrafts;
	}

	public void setNumberOfOverdrafts(int amountOfOverdrafts) {
		this.numberOfOverdrafts = amountOfOverdrafts;
	}
	
	public void decreaseNumberOfOverdrafts() {
		this.numberOfOverdrafts -= 1;
	}

	public Check generateOverdraftCheck(String commentary) throws Exception {
		if(this.numberOfOverdrafts > 0) {
			this.decreaseNumberOfOverdrafts();
			this.increaseDebt(overdraftAmount);
			return new Check(this.overdraftAmount.getValue(), this.getCountry(), commentary);
		}
		else {
			throw new Exception("overdraft limit reached");
		}
	}
}
