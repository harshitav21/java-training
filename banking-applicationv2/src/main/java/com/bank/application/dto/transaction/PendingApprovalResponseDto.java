package com.bank.application.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PendingApprovalResponseDto {
	private String transactionRef;
	private String accountNumber;
	private BigDecimal amount;

	private String performedBy;
	private LocalDateTime requestedAt;
}
