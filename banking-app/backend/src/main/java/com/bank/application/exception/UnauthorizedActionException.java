package com.bank.application.exception;

public class UnauthorizedActionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8180071765735772927L;

	public UnauthorizedActionException(String message) {
		super(message);
	}
}
