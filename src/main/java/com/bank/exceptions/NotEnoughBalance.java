package com.bank.exceptions;

public class NotEnoughBalance extends RuntimeException {

    public NotEnoughBalance() {
        super("Not enough balance in account: invalid operation.");
    }
}
