package com.bank.application.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bank.application.dto.transaction.DepositRequestDto;
import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.dto.transaction.WithdrawRequestDto;
import com.bank.application.entity.User;
import com.bank.application.service.TransactionService;
import com.bank.application.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;
	private final UserService userService; // ✅ add this

	public TransactionController(TransactionService transactionService, UserService userService) {
		this.transactionService = transactionService;
		this.userService = userService;
	}

	// ================= DEPOSIT =================
	@PostMapping("/deposit")
	@PreAuthorize("hasRole('CLERK')")
	public ResponseEntity<TransactionResponseDto> deposit(@Valid @RequestBody DepositRequestDto request) {

		User user = getLoggedInUser();

		TransactionResponseDto response = transactionService.deposit(request, user.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// ================= WITHDRAW =================
	@PostMapping("/withdraw")
	@PreAuthorize("hasRole('CLERK')")
	public ResponseEntity<TransactionResponseDto> withdraw(@Valid @RequestBody WithdrawRequestDto request) {

		User user = getLoggedInUser();

		TransactionResponseDto response = transactionService.withdraw(request, user.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// ================= HISTORY =================
	@GetMapping("/history/{accountNumber}")
	@PreAuthorize("hasAnyRole('CLERK','MANAGER')")
	public ResponseEntity<Page<TransactionHistoryResponseDto>> getTransactionHistory(@PathVariable String accountNumber,
			Pageable pageable) {

		User user = getLoggedInUser();

		return ResponseEntity.ok(transactionService.getTransactionHistory(accountNumber, user.getId(), pageable));
	}

	// ================= COMMON METHOD =================
	private User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		return userService.findByUsername(username);
	}
}
