package com.example.sis.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sis.dto.UserRoleDto;
import com.example.sis.entity.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	UserRole userRoleDtoToUserRoleEntity(UserRoleDto userRoleDto);
	
	default List<String> userRolesEntityToUserRolesDto(List<UserRole> userRole) {
		return userRole.stream()
				.map(r -> new String(r.getRole()))
				.toList();
	}

}
