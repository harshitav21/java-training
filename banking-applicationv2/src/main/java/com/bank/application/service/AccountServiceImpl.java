package com.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;
import com.bank.application.entity.Account;
import com.bank.application.entity.Role;
import com.bank.application.entity.User;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.exception.UnauthorizedActionException;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.UserRepository;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;

	public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
	}

	@Override
	public AccountResponseDto createAccount(CreateAccountRequestDto request, Long managerId) {

		User manager = userRepository.findById(managerId).orElseThrow(() -> new ResourceNotFoundException("Manager not found: " + managerId));

		if (manager.getRole() != Role.MANAGER) {
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

		return new AccountResponseDto(savedAccount.getAccountNumber(), savedAccount.getClientName(),
				savedAccount.getBalance(), savedAccount.isActive(), savedAccount.getCreatedAt());
	}

	@Override
	@Transactional(readOnly = true)
	public AccountResponseDto getAccountByNumber(String accountNumber) {

		Account account = accountRepository.findByAccountNumberAndActiveTrue(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountNumber));

		return new AccountResponseDto(account.getAccountNumber(), account.getClientName(), account.getBalance(),
				account.isActive(), account.getCreatedAt());
	}
	
	@Override
	public List<AccountResponseDto> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll(); // Fetch all accounts from DB

		// Manually convert Account entity to AccountResponseDto
		return accounts.stream().map(acc -> new AccountResponseDto(acc.getAccountNumber(), acc.getClientName(),
				acc.getBalance(), acc.isActive(), acc.getCreatedAt())).collect(Collectors.toList());
	}
	
//	.stream() → iterates over all accounts
//
//	.map(...) → converts each Account to AccountResponseDto
//
//	.collect(Collectors.toList()) → returns a List<AccountResponseDto>

	private String generateAccountNumber() {
		return "ACC" + System.currentTimeMillis();
	}
}
