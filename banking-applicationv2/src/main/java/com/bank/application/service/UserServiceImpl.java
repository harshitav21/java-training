package com.bank.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.dto.user.CreateClerkRequestDto;
import com.bank.application.dto.user.UserResponseDto;
import com.bank.application.entity.Role;
import com.bank.application.entity.User;
import com.bank.application.exception.ResourceNotFoundException;
import com.bank.application.mapper.UserMapper;
import com.bank.application.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	@SuppressWarnings("unused")
	@Override
	public UserResponseDto createClerk(CreateClerkRequestDto request, Long managerId) {

		// 1. Validate manager
		User manager = userRepository.findById(managerId).orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID: " + managerId));

		// 2. Map DTO → Entity
		User clerk = userMapper.toEntity(request);

		// 3. Set additional fields
		clerk.setRole(Role.CLERK);
//		clerk.setManager(manager);

		// 4. Save clerk
		User savedClerk = userRepository.save(clerk);

		// 5. Map Entity → Response DTO
		return userMapper.toResponseDto(savedClerk);
	}

	@Override
	public List<UserResponseDto> getAllClerks() {

		List<User> clerks = userRepository.findByRole(Role.CLERK);

		return clerks.stream().map(userMapper::toResponseDto).collect(Collectors.toList());
		
//		List<UserResponseDto> result = new ArrayList<>();
//
//		for (User user : clerks) {
//		    UserResponseDto dto = userMapper.toResponseDto(user);
//		    result.add(dto);
//		}
//
//		return result;

	}
}