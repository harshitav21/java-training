package com.bank.application.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
	private Long id;
	private String username;
	private String role;
	private boolean active;
	private LocalDateTime createdAt;
}
