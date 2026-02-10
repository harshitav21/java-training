package com.bank.application.exception;

public class InsufficientBalanceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8835939405162949462L;

	public InsufficientBalanceException(String message) {
		super(message);
	}
	
}
