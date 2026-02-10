package com.bank.application.exception;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 618066948510772228L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
