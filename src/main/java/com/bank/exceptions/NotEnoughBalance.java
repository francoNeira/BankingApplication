package com.bank.exceptions;

public class NotEnoughBalance extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughBalance() {
        super("Not enough balance in account: invalid operation.");
    }
}
