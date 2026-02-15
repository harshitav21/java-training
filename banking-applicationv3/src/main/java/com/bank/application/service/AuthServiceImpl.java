package com.bank.application.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.bank.application.dto.auth.LoginRequestDto;
import com.bank.application.dto.auth.LoginResponseDto;
import com.bank.application.entity.User;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.repository.UserRepository;
import com.bank.application.security.JwtService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final JwtService jwtService;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@Override
	public LoginResponseDto login(LoginRequestDto request) {

		log.info("Login attempt for username={}", request.getUsername());

		try {

			// 1️⃣ Authenticate
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			// 2️⃣ Fetch user
			User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
				log.warn("Authenticated but user not found in DB. username={}", request.getUsername());
				return new ResourceNotFoundException("User not found");
			});

			String token = jwtService.generateToken(user);

			log.info("Login successful for username={}, role={}", user.getUsername(), user.getRole());

			return new LoginResponseDto(token, user.getRole().name(), user.getUsername());

		} catch (Exception ex) {

			log.warn("Login failed for username={}", request.getUsername());
			throw ex;
		}
	}
}
