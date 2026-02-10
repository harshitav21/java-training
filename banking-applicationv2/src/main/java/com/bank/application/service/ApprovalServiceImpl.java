package com.bank.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.account.AccountResponseDto;
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

@Service
@Transactional
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

		Transaction transaction = transactionRepository.findByTransactionRef(transactionRef)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionRef));

		if (transaction.getStatus() != TransactionStatus.PENDING_APPROVAL) {
			throw new InvalidTransactionException("Transaction already processed");
		}

		User manager = userRepository.findById(managerId)
				.orElseThrow(() -> new ResourceNotFoundException("Manager not found: " + managerId));

		if (manager.getRole() != Role.MANAGER) {
			throw new UnauthorizedActionException("Only managers can approve withdrawals");
		}

		if (request.isApprove()) {

			Account account = transaction.getAccount();

			if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
				throw new InsufficientBalanceException("Insufficient balance");
			}

			account.setBalance(account.getBalance().subtract(transaction.getAmount()));

			transaction.setStatus(TransactionStatus.COMPLETED);

		} else {
			transaction.setStatus(TransactionStatus.REJECTED);
		}

		transaction.setApprovedBy(manager);
		transaction.setApprovedAt(LocalDateTime.now());

		return transactionMapper.toResponse(transaction);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PendingApprovalResponseDto> getPendingApprovals() {

		return transactionRepository.findByStatus(TransactionStatus.PENDING_APPROVAL).stream()
				.map(transactionMapper::toPendingApproval).toList();
	}
}
