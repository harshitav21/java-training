package com.bank.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;
import com.bank.application.dto.account.UpdateAccountRequestDto;
import com.bank.application.entity.User;
import com.bank.application.service.AccountService;
import com.bank.application.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@PreAuthorize("hasAnyRole('MANAGER','CLERK')") // default rule for controller
public class AccountController {

	private final AccountService accountService;
	private final UserService userService;

	public AccountController(AccountService accountService, UserService userService) {
		this.accountService = accountService;
		this.userService = userService;
	}

	// 🔐 Only Manager can create account
	@PreAuthorize("hasRole('MANAGER')")
	@PostMapping
	public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody CreateAccountRequestDto request) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		User manager = userService.findByUsername(username);

		AccountResponseDto response = accountService.createAccount(request, manager.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDto> updateAccount(@PathVariable String accountNumber,
			@Valid @RequestBody UpdateAccountRequestDto request) {

		return ResponseEntity.ok(accountService.updateAccount(accountNumber, request));
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<String> deactivateAccount(@PathVariable String accountNumber) {

	    accountService.deactivateAccount(accountNumber);

	    return ResponseEntity.ok("Account deactivated successfully");
	}


	// 🔐 Both Manager & Clerk can view account
	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDto> getAccount(@PathVariable String accountNumber) {

		return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
	}

	@GetMapping
	public ResponseEntity<Page<AccountResponseDto>> getAllAccounts(Pageable pageable) {

		return ResponseEntity.ok(accountService.getAllAccounts(pageable));
	}

	@GetMapping("/active")
	public ResponseEntity<Page<AccountResponseDto>> getAllActiveAccounts(Pageable pageable) {

		return ResponseEntity.ok(accountService.getAllActiveAccounts(pageable));
	}
}
