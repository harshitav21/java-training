package com.bank.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;

public interface AccountService {
	AccountResponseDto createAccount(CreateAccountRequestDto request, Long managerId);

	AccountResponseDto getAccountByNumber(String accountNumber);
	
	Page<AccountResponseDto> getAllAccounts(Pageable pageable);

	Page<AccountResponseDto> getAllActiveAccounts(Pageable pageable);
	
//	List<AccountResponseDto> getAllAccounts();
//	
//	List<AccountResponseDto> getAllActiveAccounts();
}
