package com.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.transaction.DepositRequestDto;
import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.dto.transaction.WithdrawRequestDto;
import com.bank.application.entity.Account;
import com.bank.application.entity.Role;
import com.bank.application.entity.Transaction;
import com.bank.application.entity.TransactionStatus;
import com.bank.application.entity.TransactionType;
import com.bank.application.entity.User;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.mapper.TransactionMapper;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionRepository;
import com.bank.application.repository.UserRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private final AccountRepository accountRepo;
	private final TransactionRepository txnRepo;
	private final UserRepository userRepo;
	private final TransactionMapper transactionMapper;

	public TransactionServiceImpl(AccountRepository accountRepo, TransactionRepository txnRepo,
			UserRepository userRepo,  TransactionMapper transactionMapper) {
		this.accountRepo = accountRepo;
		this.txnRepo = txnRepo;
		this.userRepo = userRepo;
		this.transactionMapper = transactionMapper;
	}

	@Override
	public TransactionResponseDto deposit(DepositRequestDto request, Long clerkId) {

		Account account = accountRepo.findByAccountNumberAndActiveTrue(request.getAccountNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + request.getAccountNumber()));

		User clerk = userRepo.findById(clerkId).orElseThrow(() -> new ResourceNotFoundException("Clerk not found: " + clerkId));

		// Update balance
		account.setBalance(account.getBalance().add(request.getAmount()));

		// Create transaction
		Transaction txn = new Transaction();
		txn.setTransactionRef(UUID.randomUUID().toString());
		txn.setAccount(account);
		txn.setType(TransactionType.DEPOSIT);
		txn.setAmount(request.getAmount());
		txn.setStatus(TransactionStatus.COMPLETED);
		txn.setPerformedBy(clerk);
		txn.setCreatedAt(LocalDateTime.now());

		txnRepo.save(txn);

		return transactionMapper.toResponse(txn);
	}

	@Override
	public TransactionResponseDto withdraw(WithdrawRequestDto request, Long clerkId) {

		Account account = accountRepo.findByAccountNumberAndActiveTrue(request.getAccountNumber())
				.orElseThrow(() ->new ResourceNotFoundException("Account not found: " + request.getAccountNumber()));

		User clerk = userRepo.findById(clerkId).orElseThrow(() -> new ResourceNotFoundException("Clerk not found: " + clerkId));

		if (account.getBalance().compareTo(request.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}

		Transaction txn = new Transaction();
		txn.setTransactionRef(UUID.randomUUID().toString());
		txn.setAccount(account);
		txn.setType(TransactionType.WITHDRAWAL);
		txn.setAmount(request.getAmount());
		txn.setPerformedBy(clerk);
		txn.setCreatedAt(LocalDateTime.now());

		if (request.getAmount().compareTo(BigDecimal.valueOf(200_000)) <= 0) {

			// Direct withdrawal
			account.setBalance(account.getBalance().subtract(request.getAmount()));
			txn.setStatus(TransactionStatus.COMPLETED);

		} else {
			// Needs approval
			txn.setStatus(TransactionStatus.PENDING_APPROVAL);
		}

		txnRepo.save(txn);
		return transactionMapper.toResponse(txn);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber, Long requesterId) {

		User requester = userRepo.findById(requesterId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		List<Transaction> transactions = txnRepo.findByAccount_AccountNumber(accountNumber);

		// Simple role check (v1)
		if (requester.getRole() == Role.CLERK) {
			// optional: restrict further later
		}

		return transactions.stream().map(transactionMapper::toHistory).toList();
	}

}
