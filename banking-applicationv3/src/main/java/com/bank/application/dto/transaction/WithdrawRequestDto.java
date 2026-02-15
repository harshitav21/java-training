package com.bank.application.dto.transaction;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequestDto {
	private String accountNumber;
	private BigDecimal amount;
}
