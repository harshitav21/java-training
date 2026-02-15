package com.bank.application.exception;

public class InvalidTransactionException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6754852487803643962L;

	public InvalidTransactionException(String message) {
		super(message);
	}
	
}
