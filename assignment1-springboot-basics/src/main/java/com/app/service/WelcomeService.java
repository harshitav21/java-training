package com.app.service;

import org.springframework.stereotype.Service;

import com.app.validation.NameValidator;

@Service
public class WelcomeService {
	
	private final NameValidator validator;

    public WelcomeService(NameValidator validator) {
        this.validator = validator;
    }
	public String greet(String name) {
		if (!validator.isValidName(name)) {
            return "Invalid name!";
        }
        return "Welcome " + name + " to Spring Boot!";
    }
}
