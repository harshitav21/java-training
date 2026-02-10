package com.bank.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.application.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//save()
//findById()
//findAll()
//deleteById()
//	boolean existsByEmail(String email);
//	boolean existsByPhone(String phone);
}
