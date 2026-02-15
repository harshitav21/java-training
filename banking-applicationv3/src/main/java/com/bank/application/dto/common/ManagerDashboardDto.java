package com.bank.application.dto.common;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagerDashboardDto {

    private long totalAccounts;
    private long activeAccounts;
    private long pendingWithdrawals;
    private BigDecimal totalBalance;
}
