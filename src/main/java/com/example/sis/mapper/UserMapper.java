package com.example.sis.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.sis.dto.AddressDto;
import com.example.sis.dto.UserDto;
import com.example.sis.dto.request.UpdateUserRequest;
import com.example.sis.dto.request.publicRequest.CreateUserAccountRequest;
import com.example.sis.entity.Address;
import com.example.sis.entity.User;
import com.example.sis.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "createdAt", ignore = true)
//	@Mapping(target = "updatedAt", ignore = true)
//	User userDtoToUserEntity(CreateUserRequest createUserDto);
	
	default UserRole userRoleDtoToUserRoleEntity(String role) {
		return UserRole.builder()
				.role(role)
				.build();
	}
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	Address addressDtoToAddressEntity(AddressDto addressDto);
	
	UserDto userEntityToUserDto(User user);
	
	default String userRoleToUserRoleDto(UserRole userRole) {
		return userRole.getRole().toString();
	}
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "addresses", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateUserEntity(UpdateUserRequest updateUserRequest, @MappingTarget User user);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "firstname", ignore = true)
	@Mapping(target = "middlename", ignore = true)
	@Mapping(target = "lastname", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "addresses", ignore = true)
	@Mapping(target = "birthDate", ignore = true)	
	User createUserAccountToUserEntity(CreateUserAccountRequest createUserAccountRequest);

}
