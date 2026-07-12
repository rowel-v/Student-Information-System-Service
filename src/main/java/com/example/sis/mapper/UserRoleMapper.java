package com.example.sis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sis.dto.UserRoleDto;
import com.example.sis.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	UserRole userRoleDtoToUserRoleEntity(UserRoleDto userRoleDto);
	
	
	
	

}
