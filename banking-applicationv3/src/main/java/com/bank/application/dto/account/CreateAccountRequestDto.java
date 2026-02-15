package com.bank.application.dto.account;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequestDto {
	private String clientName;
	private BigDecimal initialDeposit;
}
