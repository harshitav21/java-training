package com.bank.application.service;

import java.util.List;

import com.bank.application.dto.transaction.ApprovalRequestDto;
import com.bank.application.dto.transaction.PendingApprovalResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;

public interface ApprovalService {
	TransactionResponseDto approveOrReject(String transactionRef, ApprovalRequestDto request, Long managerId);

	List<PendingApprovalResponseDto> getPendingApprovals();
}
