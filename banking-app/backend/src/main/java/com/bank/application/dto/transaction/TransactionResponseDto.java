package com.bank.application.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionResponseDto {
	private String transactionRef;
	private String accountNumber;
	private String transactionType;
	private BigDecimal amount;
	private String status;

	private String performedBy;
	private String approvedBy;

	private LocalDateTime createdAt;
	private LocalDateTime approvedAt;
}
