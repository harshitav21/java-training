package com.bank.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bank.application.dto.account.AccountResponseDto;
import com.bank.application.dto.account.CreateAccountRequestDto;
import com.bank.application.dto.account.UpdateAccountRequestDto;
import com.bank.application.dto.account.UpdateAccountRequestDto;

public interface AccountService {
	AccountResponseDto createAccount(CreateAccountRequestDto request, Long managerId);

	AccountResponseDto getAccountByNumber(String accountNumber);
	
	Page<AccountResponseDto> getAllAccounts(Pageable pageable);

	Page<AccountResponseDto> getAllActiveAccounts(Pageable pageable);
	
	AccountResponseDto updateAccount(String accountNumber, UpdateAccountRequestDto request);
	
	void deactivateAccount(String accountNumber);

	
//	List<AccountResponseDto> getAllAccounts();
//	
//	List<AccountResponseDto> getAllActiveAccounts();
}
