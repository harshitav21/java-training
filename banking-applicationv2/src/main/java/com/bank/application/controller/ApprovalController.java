package com.bank.application.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bank.application.dto.transaction.ApprovalRequestDto;
import com.bank.application.dto.transaction.PendingApprovalResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.entity.User;
import com.bank.application.service.ApprovalService;
import com.bank.application.service.UserService;

@RestController
@RequestMapping("/api/approvals")
@PreAuthorize("hasRole('MANAGER')") // 🔒 Entire controller restricted
public class ApprovalController {

	private final ApprovalService approvalService;
	private final UserService userService;

	public ApprovalController(ApprovalService approvalService, UserService userService) {
		this.approvalService = approvalService;
		this.userService = userService;
	}

	// Get all pending withdrawal approvals
	@GetMapping("/pending")
	public ResponseEntity<List<PendingApprovalResponseDto>> getPendingApprovals() {
		return ResponseEntity.ok(approvalService.getPendingApprovals());
	}

	// Approve or Reject a transaction
	@PostMapping("/{transactionRef}")
	public ResponseEntity<TransactionResponseDto> approveOrReject(@PathVariable String transactionRef,
			@RequestBody ApprovalRequestDto request) {

		// 🔐 Get logged-in manager from JWT
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String username = authentication.getName();

		User manager = userService.findByUsername(username);

		return ResponseEntity.ok(approvalService.approveOrReject(transactionRef, request, manager.getId()));
	}
}
