package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.WelcomeService;

@RestController
@RequestMapping("/api")
public class WelcomeController {
	private final WelcomeService welcomeService;

	public WelcomeController(WelcomeService service) {
		this.welcomeService = service;
	}

	@GetMapping("/welcome/{name}")
	public String welcome(@PathVariable String name) {
		return welcomeService.greet(name);
	}
}
