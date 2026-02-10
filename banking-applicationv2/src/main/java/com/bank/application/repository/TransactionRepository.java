package com.bank.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.application.entity.Transaction;
import com.bank.application.entity.TransactionStatus;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	Optional<Transaction> findByTransactionRef(String transactionRef);

	List<Transaction> findByAccount_AccountNumber(String accountNumber);

	List<Transaction> findByStatus(TransactionStatus status);

	List<Transaction> findByStatusAndAccount_AccountNumber(TransactionStatus status, String accountNumber);
}
