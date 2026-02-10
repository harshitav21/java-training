package com.bank.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private Long id;
    private String name;
    private BigDecimal balance;
    private String email;
    private String phone;
}