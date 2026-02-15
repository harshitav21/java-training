package com.bank.application.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionHistoryResponseDto {
	private String transactionRef;
	private String transactionType;
	private BigDecimal amount;
	private String status;

	private String performedBy;
	private String approvedBy;

	private LocalDateTime createdAt;
}
