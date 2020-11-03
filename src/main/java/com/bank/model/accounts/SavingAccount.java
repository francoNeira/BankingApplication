package com.bank.model.accounts;

import com.bank.model.cash.Cash;

public class SavingAccount extends Account {

	public SavingAccount(Integer id, String name, Cash extractionCommission, String country) {
		super(id, name, extractionCommission, country);
	}
}
