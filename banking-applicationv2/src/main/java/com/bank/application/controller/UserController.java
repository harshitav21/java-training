package com.bank.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bank.application.dto.user.CreateClerkRequestDto;
import com.bank.application.dto.user.UserResponseDto;
import com.bank.application.entity.User;
import com.bank.application.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('MANAGER')") // 🔒 Entire controller restricted to MANAGER
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// Manager creates a clerk
	@PostMapping("/clerks")
	public ResponseEntity<UserResponseDto> createClerk(@Valid @RequestBody CreateClerkRequestDto request) {

		// 🔐 Get logged-in manager from JWT
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		User manager = userService.findByUsername(username);

		UserResponseDto response = userService.createClerk(request, manager.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// List all clerks
	@GetMapping("/clerks")
	public ResponseEntity<List<UserResponseDto>> getAllClerks() {
		return ResponseEntity.ok(userService.getAllClerks());
	}
}
