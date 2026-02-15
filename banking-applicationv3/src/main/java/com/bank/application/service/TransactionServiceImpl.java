package com.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.bank.application.exception.InvalidTransactionException;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.exception.UnauthorizedActionException;
import com.bank.application.mapper.TransactionMapper;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionRepository;
import com.bank.application.repository.UserRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	private static final Logger log = LogManager.getLogger(TransactionServiceImpl.class);

	private final AccountRepository accountRepo;
	private final TransactionRepository txnRepo;
	private final UserRepository userRepo;
	private final TransactionMapper transactionMapper;

	public TransactionServiceImpl(AccountRepository accountRepo, TransactionRepository txnRepo, UserRepository userRepo,
			TransactionMapper transactionMapper) {
		this.accountRepo = accountRepo;
		this.txnRepo = txnRepo;
		this.userRepo = userRepo;
		this.transactionMapper = transactionMapper;
	}

	// ===========================
	// DEPOSIT
	// ===========================
	@Override
	public TransactionResponseDto deposit(DepositRequestDto request, Long clerkId) {

		log.info("Deposit request | Account: {} | ClerkId: {} | Amount: {}", request.getAccountNumber(), clerkId,
				request.getAmount());

		Account account = getActiveAccount(request.getAccountNumber());

		User clerk = getClerk(clerkId);

		validateAmount(request.getAmount(), "Deposit");

		account.setBalance(account.getBalance().add(request.getAmount()));

		Transaction txn = new Transaction();
		txn.setTransactionRef(UUID.randomUUID().toString());
		txn.setAccount(account);
		txn.setType(TransactionType.DEPOSIT);
		txn.setAmount(request.getAmount());
		txn.setStatus(TransactionStatus.COMPLETED);
		txn.setPerformedBy(clerk);
		txn.setCreatedAt(LocalDateTime.now());

		txnRepo.save(txn);

		log.info("Deposit successful | Ref: {} | New Balance: {}", txn.getTransactionRef(), account.getBalance());

		return transactionMapper.toResponse(txn);
	}

	// ===========================
	// WITHDRAW
	// ===========================
	@Override
	public TransactionResponseDto withdraw(WithdrawRequestDto request, Long clerkId) {

		log.info("Withdrawal request | Account: {} | ClerkId: {} | Amount: {}", request.getAccountNumber(), clerkId,
				request.getAmount());

		Account account = getActiveAccount(request.getAccountNumber());

		User clerk = getClerk(clerkId);

		validateAmount(request.getAmount(), "Withdrawal");

		if (account.getBalance().compareTo(request.getAmount()) < 0) {
			log.warn("Insufficient balance | Account: {} | Available: {} | Requested: {}", request.getAccountNumber(),
					account.getBalance(), request.getAmount());
			throw new InvalidTransactionException("Insufficient balance.");
		}

		Transaction txn = new Transaction();
		txn.setTransactionRef(UUID.randomUUID().toString());
		txn.setAccount(account);
		txn.setType(TransactionType.WITHDRAWAL);
		txn.setAmount(request.getAmount());
		txn.setPerformedBy(clerk);
		txn.setCreatedAt(LocalDateTime.now());

		if (request.getAmount().compareTo(BigDecimal.valueOf(200_000)) <= 0) {

			account.setBalance(account.getBalance().subtract(request.getAmount()));
			txn.setStatus(TransactionStatus.COMPLETED);

			log.info("Withdrawal successful | Ref: {} | Remaining Balance: {}", txn.getTransactionRef(),
					account.getBalance());

		} else {

			txn.setStatus(TransactionStatus.PENDING_APPROVAL);

			log.info("High value withdrawal pending approval | Ref: {} | Amount: {}", txn.getTransactionRef(),
					request.getAmount());
		}

		txnRepo.save(txn);

		return transactionMapper.toResponse(txn);
	}

	// ===========================
	// TRANSACTION HISTORY
	// ===========================
	@Override
	@Transactional(readOnly = true)
	public Page<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber, Long requesterId,
			Pageable pageable) {

		log.info("Transaction history requested | Account: {} | RequestedBy: {}", accountNumber, requesterId);

		User requester = userRepo.findById(requesterId).orElseThrow(() -> {
			log.error("User not found | ID: {}", requesterId);
			return new ResourceNotFoundException("User not found");
		});

		if (requester.getRole() != Role.MANAGER && requester.getRole() != Role.CLERK) {

			log.warn("Unauthorized history access | UserId: {}", requesterId);
			throw new UnauthorizedActionException("You are not authorized to view transaction history");
		}

		getActiveAccount(accountNumber); // validates account existence + active

		Page<Transaction> transactions = txnRepo.findByAccount_AccountNumber(accountNumber, pageable);

		log.info("Transaction history fetched | Account: {} | Records: {}", accountNumber,
				transactions.getTotalElements());

		return transactions.map(transactionMapper::toHistory);
	}

	// ===========================
	// PRIVATE HELPERS
	// ===========================

	private Account getActiveAccount(String accountNumber) {

		Account account = accountRepo.findByAccountNumberAndActiveTrue(accountNumber).orElseThrow(() -> {
			log.error("Active account not found | Account: {}", accountNumber);
			return new ResourceNotFoundException("Active account not found: " + accountNumber);
		});

		return account;
	}

	private User getClerk(Long clerkId) {

		User clerk = userRepo.findById(clerkId).orElseThrow(() -> {
			log.error("User not found | ID: {}", clerkId);
			return new ResourceNotFoundException("User not found: " + clerkId);
		});

		if (clerk.getRole() != Role.CLERK) {
			log.warn("Unauthorized transaction attempt | UserId: {}", clerkId);
			throw new UnauthorizedActionException("Only clerks can perform transactions.");
		}

		return clerk;
	}

	private void validateAmount(BigDecimal amount, String type) {

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			log.warn("Invalid {} amount: {}", type, amount);
			throw new InvalidTransactionException(type + " amount must be greater than zero.");
		}
	}
}

//package com.bank.application.service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.bank.application.dto.transaction.DepositRequestDto;
//import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
//import com.bank.application.dto.transaction.TransactionResponseDto;
//import com.bank.application.dto.transaction.WithdrawRequestDto;
//import com.bank.application.entity.Account;
//import com.bank.application.entity.Role;
//import com.bank.application.entity.Transaction;
//import com.bank.application.entity.TransactionStatus;
//import com.bank.application.entity.TransactionType;
//import com.bank.application.entity.User;
//import com.bank.application.exception.InvalidTransactionException;
//import com.bank.application.exception.ResourceNotFoundException;
//import com.bank.application.exception.UnauthorizedActionException;
//import com.bank.application.mapper.TransactionMapper;
//import com.bank.application.repository.AccountRepository;
//import com.bank.application.repository.TransactionRepository;
//import com.bank.application.repository.UserRepository;
//
//@Service
//@Transactional
//public class TransactionServiceImpl implements TransactionService {
//
//	private static final Logger log = LogManager.getLogger(TransactionServiceImpl.class);
//
//	private final AccountRepository accountRepo;
//	private final TransactionRepository txnRepo;
//	private final UserRepository userRepo;
//	private final TransactionMapper transactionMapper;
//
//	public TransactionServiceImpl(AccountRepository accountRepo, TransactionRepository txnRepo, UserRepository userRepo,
//			TransactionMapper transactionMapper) {
//		this.accountRepo = accountRepo;
//		this.txnRepo = txnRepo;
//		this.userRepo = userRepo;
//		this.transactionMapper = transactionMapper;
//	}
//
//	// ===========================
//	// DEPOSIT
//	// ===========================
//	@Override
//	public TransactionResponseDto deposit(DepositRequestDto request, Long clerkId) {
//
//		log.info("Deposit request received | Account: {} | ClerkId: {} | Amount: {}", request.getAccountNumber(),
//				clerkId, request.getAmount());
//
//		Account account = accountRepo.findByAccountNumberAndActiveTrue(request.getAccountNumber()).orElseThrow(() -> {
//			log.error("Deposit failed - Account not found: {}", request.getAccountNumber());
//			return new ResourceNotFoundException("Account not found: " + request.getAccountNumber());
//		});
//
//		User clerk = userRepo.findById(clerkId).orElseThrow(() -> {
//			log.error("Deposit failed - Clerk not found: {}", clerkId);
//			return new ResourceNotFoundException("Clerk not found: " + clerkId);
//		});
//
//		if (clerk.getRole() != Role.CLERK) {
//			log.warn("Unauthorized deposit attempt by UserId: {}", clerkId);
//			throw new UnauthorizedActionException("Only clerks can perform transactions.");
//		}
//
//		if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//			log.warn("Invalid deposit amount for Account: {}", request.getAccountNumber());
//			throw new InvalidTransactionException("Deposit amount must be greater than zero.");
//		}
//
//		// Update balance
//		account.setBalance(account.getBalance().add(request.getAmount()));
//
//		// Create transaction
//		Transaction txn = new Transaction();
//		txn.setTransactionRef(UUID.randomUUID().toString());
//		txn.setAccount(account);
//		txn.setType(TransactionType.DEPOSIT);
//		txn.setAmount(request.getAmount());
//		txn.setStatus(TransactionStatus.COMPLETED);
//		txn.setPerformedBy(clerk);
//		txn.setCreatedAt(LocalDateTime.now());
//
//		txnRepo.save(txn);
//
//		log.info("Deposit successful | Ref: {} | New Balance: {}", txn.getTransactionRef(), account.getBalance());
//
//		return transactionMapper.toResponse(txn);
//	}
//
//	// ===========================
//	// WITHDRAW
//	// ===========================
//	@Override
//	public TransactionResponseDto withdraw(WithdrawRequestDto request, Long clerkId) {
//
//		log.info("Withdrawal request received | Account: {} | ClerkId: {} | Amount: {}", request.getAccountNumber(),
//				clerkId, request.getAmount());
//
//		Account account = accountRepo.findByAccountNumberAndActiveTrue(request.getAccountNumber()).orElseThrow(() -> {
//			log.error("Withdrawal failed - Account not found: {}", request.getAccountNumber());
//			return new ResourceNotFoundException("Account not found: " + request.getAccountNumber());
//		});
//
//		User clerk = userRepo.findById(clerkId).orElseThrow(() -> {
//			log.error("Withdrawal failed - Clerk not found: {}", clerkId);
//			return new ResourceNotFoundException("Clerk not found: " + clerkId);
//		});
//
//		if (clerk.getRole() != Role.CLERK) {
//			log.warn("Unauthorized withdrawal attempt by UserId: {}", clerkId);
//			throw new UnauthorizedActionException("Only clerks can perform transactions.");
//		}
//
//		if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//			log.warn("Invalid withdrawal amount for Account: {}", request.getAccountNumber());
//			throw new InvalidTransactionException("Withdrawal amount must be greater than zero.");
//		}
//
//		if (account.getBalance().compareTo(request.getAmount()) < 0) {
//			log.warn("Insufficient balance | Account: {} | Available: {} | Requested: {}", request.getAccountNumber(),
//					account.getBalance(), request.getAmount());
//			throw new InvalidTransactionException("Insufficient balance.");
//		}
//
//		Transaction txn = new Transaction();
//		txn.setTransactionRef(UUID.randomUUID().toString());
//		txn.setAccount(account);
//		txn.setType(TransactionType.WITHDRAWAL);
//		txn.setAmount(request.getAmount());
//		txn.setPerformedBy(clerk);
//		txn.setCreatedAt(LocalDateTime.now());
//
//		if (request.getAmount().compareTo(BigDecimal.valueOf(200_000)) <= 0) {
//
//			account.setBalance(account.getBalance().subtract(request.getAmount()));
//			txn.setStatus(TransactionStatus.COMPLETED);
//
//			log.info("Withdrawal successful | Ref: {} | Remaining Balance: {}", txn.getTransactionRef(),
//					account.getBalance());
//
//		} else {
//
//			txn.setStatus(TransactionStatus.PENDING_APPROVAL);
//
//			log.info("Withdrawal requires approval | Ref: {} | Amount: {}", txn.getTransactionRef(),
//					request.getAmount());
//		}
//
//		txnRepo.save(txn);
//		return transactionMapper.toResponse(txn);
//	}
//
//	// ===========================
//	// TRANSACTION HISTORY
//	// ===========================
//	@Override
//	public Page<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber, Long requesterId,
//			Pageable pageable) {
//
//		log.info("Transaction history requested | Account: {} | RequestedBy: {}", accountNumber, requesterId);
//
//		User requester = userRepo.findById(requesterId).orElseThrow(() -> {
//			log.error("History request failed - User not found: {}", requesterId);
//			return new ResourceNotFoundException("User not found");
//		});
//
//		if (requester.getRole() != Role.MANAGER && requester.getRole() != Role.CLERK) {
//			log.warn("Unauthorized history access attempt by UserId: {}", requesterId);
//			throw new UnauthorizedActionException("You are not authorized to view transaction history");
//		}
//
//		Account account = accountRepo.findByAccountNumberAndActiveTrue(accountNumber).orElseThrow(() -> {
//			log.error("History request failed - Account not found: {}", accountNumber);
//			return new ResourceNotFoundException("Account not found");
//		});
//
//		Page<Transaction> transactions = txnRepo.findByAccount_AccountNumber(accountNumber, pageable);
//
//		log.info("Transaction history fetched successfully | Account: {} | Records: {}", accountNumber,
//				transactions.getTotalElements());
//
//		return transactions.map(transactionMapper::toHistory);
//	}
//	
//	@SuppressWarnings("unused")
//	private Account getActiveAccount(String accountNumber) {
//
//	    Account account = accountRepo.findByAccountNumber(accountNumber)
//	            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountNumber));
//
//	    if (!account.isActive()) {
//	        throw new InvalidTransactionException("Account is inactive");
//	    }
//
//	    return account;
//	}
//
//}
