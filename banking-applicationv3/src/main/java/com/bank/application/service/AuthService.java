package com.bank.application.service;

import com.bank.application.dto.auth.LoginRequestDto;
import com.bank.application.dto.auth.LoginResponseDto;

public interface AuthService {
	LoginResponseDto login(LoginRequestDto request);
}
