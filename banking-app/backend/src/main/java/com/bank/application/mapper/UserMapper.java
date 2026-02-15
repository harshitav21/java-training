package com.bank.application.mapper;

import org.mapstruct.Mapper;

import com.bank.application.dto.user.CreateClerkRequestDto;
import com.bank.application.dto.user.UserResponseDto;
import com.bank.application.entity.User;

@Mapper(componentModel = "spring")
//by writing spring, the object becomes injectable, without this boilerplate code to bann jaata hai par 
//inject nahi hota automatically
public interface UserMapper {
	User toEntity(CreateClerkRequestDto dto);

	UserResponseDto toResponseDto(User user);
}
