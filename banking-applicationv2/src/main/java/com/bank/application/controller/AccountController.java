package com.bank.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;
import com.bank.application.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	// Manager creates an account
	@PostMapping
	public ResponseEntity<AccountResponseDto> createAccount(@RequestBody CreateAccountRequestDto request,
			@RequestParam Long managerId) {

		AccountResponseDto response = accountService.createAccount(request, managerId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get account by account number
	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountResponseDto> getAccount(@PathVariable String accountNumber) {

		return ResponseEntity.ok(accountService.getAccountByNumber(accountNumber));
	}
	
	// List all accounts
	@GetMapping
	public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
	    List<AccountResponseDto> accounts = accountService.getAllAccounts();
	    return ResponseEntity.ok(accounts);
	}
}
