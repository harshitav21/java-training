package com.bank.application.service;

import java.util.List;

import com.bank.application.dto.user.CreateClerkRequestDto;
import com.bank.application.dto.user.UserResponseDto;
import com.bank.application.entity.User;

public interface UserService {
	UserResponseDto createClerk(CreateClerkRequestDto request, Long managerId);

	List<UserResponseDto> getAllClerks();
	
	User findByUsername(String username);

}
