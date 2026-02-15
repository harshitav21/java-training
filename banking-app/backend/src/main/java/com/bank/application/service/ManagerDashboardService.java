package com.bank.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.common.ManagerDashboardDto;
import com.bank.application.entity.TransactionStatus;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionRepository;

@Service
@Transactional(readOnly = true)
public class ManagerDashboardService {

	private final AccountRepository accountRepo;
	private final TransactionRepository txnRepo;

	public ManagerDashboardService(AccountRepository accountRepo, TransactionRepository txnRepo) {
		this.accountRepo = accountRepo;
		this.txnRepo = txnRepo;
	}

	public ManagerDashboardDto getDashboardStats() {

		long totalAccounts = accountRepo.count();
		long activeAccounts = accountRepo.countByActiveTrue();
		long pendingWithdrawals = txnRepo.countByStatus(TransactionStatus.PENDING_APPROVAL);

		BigDecimal totalBalance = accountRepo.findAll().stream().map(acc -> acc.getBalance()).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		return new ManagerDashboardDto(totalAccounts, activeAccounts, pendingWithdrawals, totalBalance);
	}
}
