package com.bank.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bank.application.dto.AccountRequestDto;
import com.bank.application.dto.AccountResponseDto;
import com.bank.application.entity.Account;
import com.bank.application.exception.InsufficientBalanceException;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.repository.AccountRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public AccountResponseDto createAccount(AccountRequestDto requestDto) {

		Account account = new Account();
		account.setName(requestDto.getName());
		account.setBalance(requestDto.getBalance());
		account.setEmail(requestDto.getEmail());
		account.setPhone(requestDto.getPhone());

		Account savedAccount = accountRepository.save(account);

		return mapToResponseDto(savedAccount);
	}

	@Override
	public AccountResponseDto getAccountById(Long id) {

		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

		return mapToResponseDto(account);
	}

	@Override
	public List<AccountResponseDto> getAllAccounts() {

		return accountRepository.findAll().stream().map(this::mapToResponseDto).collect(Collectors.toList());
	}

	@Override
	public void deposit(Long accId, BigDecimal amount) {

		Account account = accountRepository.findById(accId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accId));

		account.setBalance(account.getBalance().add(amount));
	}

	@Override
	public void withdraw(Long accId, BigDecimal amount) {

		Account account = accountRepository.findById(accId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accId));

		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient balance");
		}

		account.setBalance(account.getBalance().subtract(amount));
	}

	@Override
	public void transfer(Long fromAccId, Long toAccId, BigDecimal amount) {

		if (fromAccId.equals(toAccId)) {
			throw new IllegalArgumentException("Source and target accounts cannot be same");
		}

		Account fromAccount = accountRepository.findById(fromAccId)
				.orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

		Account toAccount = accountRepository.findById(toAccId)
				.orElseThrow(() -> new ResourceNotFoundException("Target account not found"));

		if (fromAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException("Insufficient balance");
		}

		fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
		toAccount.setBalance(toAccount.getBalance().add(amount));
	}

	private AccountResponseDto mapToResponseDto(Account account) {

		AccountResponseDto dto = new AccountResponseDto();
		dto.setId(account.getId());
		dto.setName(account.getName());
		dto.setBalance(account.getBalance());
		dto.setEmail(account.getEmail());
		dto.setPhone(account.getPhone());

		return dto;
	}

}
