package com.bank;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.Account;
import com.bank.model.Cash;

import utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BankingApplication {
    private final List<Account> accounts;
    private final IdGenerator idGenerator;

    public BankingApplication() {
        accounts = new ArrayList<>();
        idGenerator = new IdGenerator();
    }

    // Getters
    public List<Account> accounts() {
        return accounts;
    }

    /**
     * Returns the account created from the received customer name. Also adds it to the account list.
     *
     * @return the account created from the received customer name.
     */
    public Integer register(String customerName) {
        Account account = new Account(idGenerator.nextId(), customerName);
        accounts.add(account);
        return account.customerId();
    }

    /**
     * Deposits the received amount in the specified account.
     */
    public void doDeposit(Account account, Cash cash) {
        account.deposit(cash);
    }

    /**
     * Withdrams the received amount from the specified account.
     *
     * @throws NotEnoughBalance if account has not enought balance for the operation.
     */
    public void doWithdrawal(Account account, Cash cash) throws NotEnoughBalance {
    	try {
    		if (!accountHasNotEnoughBalance(account, cash))
    			account.withdraw(cash);
    	}catch(NoSuchElementException exception){
    		throw new NotEnoughBalance();
    	}
    }

    /**
     * Returns the account corresponding to the received id.
     *
     * @return the account corresponding to the received id.
     */
    public Account findById(Integer id) {
        return accounts.stream().filter(account -> account.hasId(id)).findFirst().get();
    }

    // Auxiliary methods

    /**
     * Returns true if received amount is bigger that account's balance.
     *
     * @return true if received amount is bigger that account's balance.
     */
    private boolean accountHasNotEnoughBalance(Account account, Cash cash) { 
    		return account.searchRequiredCash(cash).getValue() < cash.getValue();
    }
}
