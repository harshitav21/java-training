package com.bank.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.transaction.ApprovalRequestDto;
import com.bank.application.dto.transaction.PendingApprovalResponseDto;
import com.bank.application.dto.transaction.TransactionResponseDto;
import com.bank.application.entity.Account;
import com.bank.application.entity.Role;
import com.bank.application.entity.Transaction;
import com.bank.application.entity.TransactionStatus;
import com.bank.application.entity.User;
import com.bank.application.exception.InsufficientBalanceException;
import com.bank.application.exception.InvalidTransactionException;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.exception.UnauthorizedActionException;
import com.bank.application.mapper.TransactionMapper;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionRepository;
import com.bank.application.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final TransactionMapper transactionMapper;

	public ApprovalServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository,
			UserRepository userRepository, TransactionMapper transactionMapper) {

		this.transactionRepository = transactionRepository;
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.transactionMapper = transactionMapper;
	}

	@Override
	public TransactionResponseDto approveOrReject(String transactionRef, ApprovalRequestDto request, Long managerId) {

		log.info("Approval request received. transactionRef={}, managerId={}", transactionRef, managerId);

		Transaction transaction = transactionRepository.findByTransactionRef(transactionRef).orElseThrow(() -> {
			log.warn("Transaction not found. transactionRef={}", transactionRef);
			return new ResourceNotFoundException("Transaction not found: " + transactionRef);
		});

		if (transaction.getStatus() != TransactionStatus.PENDING_APPROVAL) {
			log.warn("Transaction already processed. transactionRef={}, currentStatus={}", transactionRef,
					transaction.getStatus());
			throw new InvalidTransactionException("Transaction already processed");
		}

		User manager = userRepository.findById(managerId).orElseThrow(() -> {
			log.warn("Manager not found. managerId={}", managerId);
			return new ResourceNotFoundException("Manager not found: " + managerId);
		});

		if (manager.getRole() != Role.MANAGER) {
			log.warn("Unauthorized approval attempt by userId={} with role={}", managerId, manager.getRole());
			throw new UnauthorizedActionException("Only managers can approve withdrawals");
		}

		if (request.isApprove()) {

			Account account = transaction.getAccount();

			log.info("Manager {} approving transactionRef={} for amount={}", managerId, transactionRef,
					transaction.getAmount());

			if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
				log.error("Insufficient balance for transactionRef={}. Available={}, Required={}", transactionRef,
						account.getBalance(), transaction.getAmount());
				throw new InsufficientBalanceException("Insufficient balance");
			}

			account.setBalance(account.getBalance().subtract(transaction.getAmount()));
			accountRepository.save(account);

			transaction.setStatus(TransactionStatus.COMPLETED);

			log.info("Transaction approved successfully. transactionRef={}, managerId={}", transactionRef, managerId);

		} else {

			transaction.setStatus(TransactionStatus.REJECTED);

			log.info("Transaction rejected. transactionRef={}, managerId={}", transactionRef, managerId);
		}

		transaction.setApprovedBy(manager);
		transaction.setApprovedAt(LocalDateTime.now());

		transactionRepository.save(transaction);

		return transactionMapper.toResponse(transaction);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PendingApprovalResponseDto> getPendingApprovals() {

		log.debug("Fetching all pending approval transactions");

		return transactionRepository.findByStatus(TransactionStatus.PENDING_APPROVAL).stream()
				.map(transactionMapper::toPendingApproval).toList();
	}
}
