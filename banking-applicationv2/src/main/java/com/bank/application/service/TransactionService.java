package com.bank.application.service;

import java.util.List;

import com.bank.application.dto.transaction.DepositRequestDto;
import com.bank.application.dto.transaction.TransactionHistoryResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.dto.transaction.WithdrawRequestDto;

public interface TransactionService {
	TransactionResponseDto deposit(DepositRequestDto request, Long clerkId);

	TransactionResponseDto withdraw(WithdrawRequestDto request, Long clerkId);

	List<TransactionHistoryResponseDto> getTransactionHistory(String accountNumber, Long requesterId);
}
