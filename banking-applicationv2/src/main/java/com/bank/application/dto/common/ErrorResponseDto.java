package com.bank.application.dto.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
	private String errorCode;
	private String message;
	private LocalDateTime timestamp;
}
