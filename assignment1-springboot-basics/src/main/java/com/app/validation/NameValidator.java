package com.app.validation;

import org.springframework.stereotype.Component;

@Component
public class NameValidator {
	public boolean isValidName(String name) {
		return name != null && !name.isBlank();
	}
}
