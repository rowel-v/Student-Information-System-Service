package com.example.sis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sis.dto.request.CreateUserRequest;
import com.example.sis.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "role", ignore = true)
	UserRole toEntity(CreateUserRequest createUserDto);
	
	default UserRole toEntity(String role) {
		return UserRole.builder()
				.role(role)
				.build();
	}
	
	
	

}
