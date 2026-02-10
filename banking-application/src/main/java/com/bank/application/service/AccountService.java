package com.bank.application.service;

import java.math.BigDecimal;
import java.util.List;

import com.bank.application.dto.AccountRequestDto;
import com.bank.application.dto.AccountResponseDto;

public interface AccountService {
	AccountResponseDto createAccount(AccountRequestDto requestDto);
	AccountResponseDto getAccountById(Long id);
	List<AccountResponseDto> getAllAccounts();
	void deposit(Long accId, BigDecimal amount);
	void withdraw(Long accId, BigDecimal amount);
	void transfer(Long fromAccId, Long toAccId, BigDecimal amount);
}
