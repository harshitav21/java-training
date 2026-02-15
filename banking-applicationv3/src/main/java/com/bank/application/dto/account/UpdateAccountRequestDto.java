package com.bank.application.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountRequestDto {

    @NotNull
    private BigDecimal balance; 

    private boolean active;
}
