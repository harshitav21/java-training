package com.bank.application.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bank.application.entity.Role;
import com.bank.application.entity.User;
import com.bank.application.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {

		// If no manager exists, create one
		if (userRepository.findByUsername("manager1").isEmpty()) {

			User manager = new User();
			manager.setUsername("manager1");
			manager.setPassword(passwordEncoder.encode("manager123"));
			manager.setRole(Role.MANAGER);
			manager.setActive(true);
			manager.setCreatedAt(LocalDateTime.now());

			userRepository.save(manager);

			System.out.println("✅ Default manager created.");
		}
	}
}
