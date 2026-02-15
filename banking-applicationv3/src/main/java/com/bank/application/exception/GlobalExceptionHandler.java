package com.bank.application.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 404 - Resource not found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	// 400 - Invalid transaction / bad request
	@ExceptionHandler({ InvalidTransactionException.class, InsufficientBalanceException.class })
	public ResponseEntity<?> handleBadRequest(RuntimeException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// 403 - Unauthorized action (custom)
	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<?> handleUnauthorized(UnauthorizedActionException ex) {
		return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
	}

	// 403 - Spring Security access denied
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
		return buildResponse(HttpStatus.FORBIDDEN, "Access Denied");
	}

	// 401 - Bad credentials
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
	}

	// 400 - Validation errors (@Valid)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {

		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Validation error");

		return buildResponse(HttpStatus.BAD_REQUEST, message);
	}

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//        return ResponseEntity.badRequest().body(errors);
//    }
	
	//JWT Expired Handler
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwt(ExpiredJwtException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, "JWT token has expired. Please login again.");
	}
	
	//Handle Invalid JWT
	@ExceptionHandler(io.jsonwebtoken.JwtException.class)
	public ResponseEntity<?> handleInvalidJwt(Exception ex) {
	    return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid JWT token.");
	}
	
//	// 500 - Generic fallback
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<?> handleGeneric(Exception ex) {
//		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
//	}

	private ResponseEntity<?> buildResponse(HttpStatus status, String message) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);

		return new ResponseEntity<>(body, status);
	}
}

//package com.bank.application.exception;
//
//import java.time.LocalDateTime;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.bank.application.dto.common.ErrorResponseDto;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex) {
//		ErrorResponseDto response = new ErrorResponseDto("RESOURCE_NOT_FOUND", ex.getMessage(), LocalDateTime.now());
//		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.NOT_FOUND);
//	}
//
//	@ExceptionHandler(InsufficientBalanceException.class)
//	public ResponseEntity<ErrorResponseDto> handleInsufficientBalance(InsufficientBalanceException ex) {
//		ErrorResponseDto response = new ErrorResponseDto("INSUFFICIENT_BALANCE", ex.getMessage(), LocalDateTime.now());
//		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.BAD_REQUEST);
//	}
//
//	@ExceptionHandler(UnauthorizedActionException.class)
//	public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedActionException ex) {
//		ErrorResponseDto response = new ErrorResponseDto("UNAUTHORIZED_ACTION", ex.getMessage(), LocalDateTime.now());
//		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.FORBIDDEN);
//	}
//
//	@ExceptionHandler(InvalidTransactionException.class)
//	public ResponseEntity<ErrorResponseDto> handleInvalidTransaction(InvalidTransactionException ex) {
//		ErrorResponseDto response = new ErrorResponseDto("INVALID_TRANSACTION", ex.getMessage(), LocalDateTime.now());
//		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//	}
//	
//	// Fallback for all other exceptions
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorResponseDto> handleGeneral(Exception ex) {
//		ErrorResponseDto response = new ErrorResponseDto("INTERNAL_SERVER_ERROR", "Something went wrong",
//				LocalDateTime.now());
//		return new ResponseEntity<ErrorResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//}