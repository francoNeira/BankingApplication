package com.bank.model;

public class Account {
    private final Integer customerId;
    private final String customerName;
    private Double balance;
    private Double previousTransaction;

    public Account(Integer id, String name) {
        this.customerId = id;
        this.customerName = name;
        this.balance = 0.0;
        this.previousTransaction = null;
    }

    public Integer customerId() {
        return customerId;
    }
    public String customerName() {
        return customerName;
    }
    public Double balance() {
        return balance;
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
    public void deposit(Double amount) {
        balance += amount;
        previousTransaction = amount;
    }

    /**
     * Decreases balance and updates previous transaction.
     */
    public void withdraw(Double amount) {
        balance -= amount;
        previousTransaction = amount * -1;
    }
}
