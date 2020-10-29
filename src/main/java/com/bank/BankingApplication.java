package com.bank;

import com.bank.exceptions.NotEnoughBalance;
import com.bank.model.Account;

import utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;

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
    public void doDeposit(Account account, Double amount) {
        account.deposit(amount);
    }

    /**
     * Withdrams the received amount from the specified account.
     *
     * @throws NotEnoughBalance if account has not enought balance for the operation.
     */
    public void doWithdrawal(Account account, Double amount) throws NotEnoughBalance {
        if (accountHasNotEnoughBalance(account, amount))
            throw new NotEnoughBalance();
        account.withdraw(amount);
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
    private boolean accountHasNotEnoughBalance(Account account, Double amount) {
        return account.balance() < amount;
    }
}
