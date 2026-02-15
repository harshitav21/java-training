package com.bank.application.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.application.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//save()
//findById()
//findAll()
//deleteById()
	Optional<Account> findByAccountNumberAndActiveTrue(String accountNumber);
	
	Page<Account> findByActiveTrue(Pageable pageable);
	
	long countByActiveTrue();
	
	Optional<Account> findByAccountNumber(String accountNumber);
}
