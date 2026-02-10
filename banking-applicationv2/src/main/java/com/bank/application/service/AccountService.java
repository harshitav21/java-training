package com.bank.application.service;

import java.util.List;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;

public interface AccountService {
	AccountResponseDto createAccount(CreateAccountRequestDto request, Long managerId);

	AccountResponseDto getAccountByNumber(String accountNumber);
	
	List<AccountResponseDto> getAllAccounts();
}
