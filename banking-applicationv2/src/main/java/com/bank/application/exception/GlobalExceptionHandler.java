package com.bank.application.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.application.dto.common.ErrorResponseDto;

import jakarta.transaction.InvalidTransactionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
		ErrorResponseDto response = new ErrorResponseDto("RESOURCE_NOT_FOUND", ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponseDto> handleInsufficientBalance(InsufficientBalanceException ex) {
		ErrorResponseDto response = new ErrorResponseDto("INSUFFICIENT_BALANCE", ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedActionException ex) {
		ErrorResponseDto response = new ErrorResponseDto("UNAUTHORIZED_ACTION", ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidTransaction(InvalidTransactionException ex) {
		ErrorResponseDto response = new ErrorResponseDto("INVALID_TRANSACTION", ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// Fallback for all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGeneral(Exception ex) {
		ErrorResponseDto response = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong",
				LocalDateTime.now());
		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}