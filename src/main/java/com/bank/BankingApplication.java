package com.bank;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.accounts.Account;
import com.bank.model.cash.Cash;

import utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BankingApplication {
    private final List<Account> accounts;

    public BankingApplication() {
        accounts = new ArrayList<>();
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
    public Integer register(Account account) {
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
    	account.withdraw(cash);
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
}
