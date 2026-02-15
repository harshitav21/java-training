package com.bank.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // ===========================
    // CREATE CLERK
    // ===========================
    @Override
    public UserResponseDto createClerk(CreateClerkRequestDto request, Long managerId) {

        log.info("Create clerk request received | ManagerId: {} | Username: {}",
                managerId, request.getUsername());

        // 1. Validate manager
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> {
                    log.error("Manager not found with ID: {}", managerId);
                    return new ResourceNotFoundException("Manager not found with ID: " + managerId);
                });

        // 2. Map DTO → Entity
        User clerk = userMapper.toEntity(request);

        // 3. Set additional fields
        clerk.setRole(Role.CLERK);
        clerk.setPassword(passwordEncoder.encode(request.getPassword())); // ✅ encode password
//      clerk.setManager(manager);

        // 4. Save clerk
        User savedClerk = userRepository.save(clerk);

        log.info("Clerk created successfully | ClerkId: {} | CreatedBy ManagerId: {}",
                savedClerk.getId(), managerId);

        // 5. Map Entity → Response DTO
        return userMapper.toResponseDto(savedClerk);
    }

    // ===========================
    // FIND BY USERNAME
    // ===========================
    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        log.info("Fetching user by username: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new ResourceNotFoundException("User not found: " + username);
                });
    }

    // ===========================
    // GET ALL CLERKS
    // ===========================
    @Override
    public List<UserResponseDto> getAllClerks() {

        log.info("Fetching all clerks");

        List<User> clerks = userRepository.findByRole(Role.CLERK);

        log.info("Total clerks found: {}", clerks.size());

        return clerks.stream().map(userMapper::toResponseDto).collect(Collectors.toList());

//      List<UserResponseDto> result = new ArrayList<>();
//
//      for (User user : clerks) {
//          UserResponseDto dto = userMapper.toResponseDto(user);
//          result.add(dto);
//      }
//
//      return result;

    }
}
