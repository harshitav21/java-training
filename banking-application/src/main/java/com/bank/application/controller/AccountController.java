package com.bank.application.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.AccountRequestDto;
import com.bank.application.dto.AccountResponseDto;
import com.bank.application.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto requestDto) {

		AccountResponseDto response = accountService.createAccount(requestDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {

		AccountResponseDto response = accountService.getAccountById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {

		List<AccountResponseDto> accounts = accountService.getAllAccounts();
		return ResponseEntity.ok(accounts);
	}

	@PutMapping("/{id}/deposit")
	public ResponseEntity<String> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {

		accountService.deposit(id, amount);
		return ResponseEntity.ok("Amount deposited successfully");
	}

	@PutMapping("/{id}/withdraw")
	public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {

		accountService.withdraw(id, amount);
		return ResponseEntity.ok("Amount withdrawn successfully");
	}

	@PutMapping("/transfer")
	public ResponseEntity<String> transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
			@RequestParam BigDecimal amount) {

		accountService.transfer(fromAccountId, toAccountId, amount);
		return ResponseEntity.ok("Transfer successful");
	}

}
