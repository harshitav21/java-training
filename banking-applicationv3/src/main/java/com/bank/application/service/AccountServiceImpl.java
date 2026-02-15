package com.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;
import com.bank.application.dto.account.UpdateAccountRequestDto;
import com.bank.application.entity.Account;
import com.bank.application.entity.Role;
import com.bank.application.entity.TransactionStatus;
import com.bank.application.entity.User;
import com.bank.application.exception.InvalidTransactionException;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.exception.UnauthorizedActionException;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionRepository;
import com.bank.application.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TransactionRepository transactionRepository;

	public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public AccountResponseDto createAccount(CreateAccountRequestDto request, Long managerId) {

		log.info("Account creation request initiated by managerId={}", managerId);

		User manager = userRepository.findById(managerId).orElseThrow(() -> {
			log.warn("Manager not found with id={}", managerId);
			return new ResourceNotFoundException("Manager not found: " + managerId);
		});

		if (manager.getRole() != Role.MANAGER) {
			log.warn("Unauthorized account creation attempt by userId={} with role={}", managerId, manager.getRole());
			throw new UnauthorizedActionException("Only managers can create accounts.");
		}

		Account account = new Account();
		account.setAccountNumber(generateAccountNumber());
		account.setClientName(request.getClientName());
		account.setBalance(request.getInitialDeposit() != null ? request.getInitialDeposit() : BigDecimal.ZERO);
		account.setActive(true);
		account.setCreatedAt(LocalDateTime.now());
		account.setCreatedBy(manager);

		Account savedAccount = accountRepository.save(account);

		log.info("Account successfully created. accountNumber={}, createdBy={}", savedAccount.getAccountNumber(),
				managerId);

		return new AccountResponseDto(savedAccount.getAccountNumber(), savedAccount.getClientName(),
				savedAccount.getBalance(), savedAccount.isActive(), savedAccount.getCreatedAt());
	}

	@Override
	@Transactional(readOnly = true)
	public AccountResponseDto getAccountByNumber(String accountNumber) {

		log.debug("Fetching account details for accountNumber={}", accountNumber);

		Account account = accountRepository.findByAccountNumberAndActiveTrue(accountNumber).orElseThrow(() -> {
			log.warn("Account not found or inactive. accountNumber={}", accountNumber);
			return new ResourceNotFoundException("Account not found: " + accountNumber);
		});

		return new AccountResponseDto(account.getAccountNumber(), account.getClientName(), account.getBalance(),
				account.isActive(), account.getCreatedAt());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AccountResponseDto> getAllAccounts(Pageable pageable) {

		log.debug("Fetching all accounts. pageNumber={}, pageSize={}", pageable.getPageNumber(),
				pageable.getPageSize());

		Page<Account> accounts = accountRepository.findAll(pageable);

		return accounts.map(account -> new AccountResponseDto(account.getAccountNumber(), account.getClientName(),
				account.getBalance(), account.isActive(), account.getCreatedAt()));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AccountResponseDto> getAllActiveAccounts(Pageable pageable) {

		log.debug("Fetching all active accounts. pageNumber={}, pageSize={}", pageable.getPageNumber(),
				pageable.getPageSize());

		Page<Account> accounts = accountRepository.findByActiveTrue(pageable);

		return accounts.map(account -> new AccountResponseDto(account.getAccountNumber(), account.getClientName(),
				account.getBalance(), account.isActive(), account.getCreatedAt()));
	}

	@Override
	public AccountResponseDto updateAccount(String accountNumber, UpdateAccountRequestDto request) {

		log.info("Update request received for accountNumber={}", accountNumber);

		Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> {
			log.warn("Account not found for update. accountNumber={}", accountNumber);
			return new ResourceNotFoundException("Account not found: " + accountNumber);
		});

		// Update allowed fields only
		account.setBalance(request.getBalance());
		account.setActive(request.isActive());

		Account updatedAccount = accountRepository.save(account);

		log.info("Account updated successfully. accountNumber={}, newBalance={}, activeStatus={}",
				updatedAccount.getAccountNumber(), updatedAccount.getBalance(), updatedAccount.isActive());

		return new AccountResponseDto(updatedAccount.getAccountNumber(), updatedAccount.getClientName(),
				updatedAccount.getBalance(), updatedAccount.isActive(), updatedAccount.getCreatedAt());
	}

	@Override
	@Transactional
	public void deactivateAccount(String accountNumber) {

		Account account = accountRepository.findByAccountNumberAndActiveTrue(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Active account not found: " + accountNumber));

		// Rule 1: Balance must be zero
		if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
			throw new InvalidTransactionException("Account cannot be deactivated. Balance must be zero.");
		}

		// Rule 2: No pending transactions
		boolean hasPendingTransactions = transactionRepository.existsByAccount_AccountNumberAndStatus(accountNumber,
				TransactionStatus.PENDING_APPROVAL);

		if (hasPendingTransactions) {
			throw new InvalidTransactionException("Account cannot be deactivated. Pending transactions exist.");
		}

		account.setActive(false);

	}

	private String generateAccountNumber() {
		return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}
}
