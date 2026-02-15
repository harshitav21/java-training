package com.bank.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.application.entity.Role;
import com.bank.application.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndActiveTrue(String username);

	boolean existsByUsername(String username);

	List<User> findByRole(Role role);
	
	Optional<User> findByUsername(String username);

}
