package com.bank.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.application.dto.transaction.DepositRequestDto;
import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.dto.transaction.WithdrawRequestDto;
import com.bank.application.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// Deposit money (performed by clerk)
	@PostMapping("/deposit")
	public ResponseEntity<TransactionResponseDto> deposit(@RequestBody DepositRequestDto request,
			@RequestParam Long clerkId) {

		TransactionResponseDto response = transactionService.deposit(request, clerkId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Withdraw money (performed by clerk)
	@PostMapping("/withdraw")
	public ResponseEntity<TransactionResponseDto> withdraw(@RequestBody WithdrawRequestDto request,
			@RequestParam Long clerkId) {

		TransactionResponseDto response = transactionService.withdraw(request, clerkId);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// Get transaction history
	@GetMapping("/history/{accountNumber}")
	public ResponseEntity<List<TransactionHistoryResponseDto>> getTransactionHistory(@PathVariable String accountNumber,
			@RequestParam Long requesterId) {

		return ResponseEntity.ok(transactionService.getTransactionHistory(accountNumber, requesterId));
	}
}
