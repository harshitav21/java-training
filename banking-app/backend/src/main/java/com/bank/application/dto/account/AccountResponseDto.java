package com.bank.application.dto.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
	private String accountNumber;
	private String clientName;
	private BigDecimal balance;
	private boolean active;
	private LocalDateTime createdAt;
}
