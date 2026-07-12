package com.example.sis.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.sis.dto.UserRoleDto;
import com.example.sis.entity.User;
import com.example.sis.entity.UserRole;
import com.example.sis.mapper.UserRoleMapper;
import com.example.sis.repository.UserRepo;
import com.example.sis.repository.UserRoleRepo;
import com.example.sis.result.userRole.CreateUserRoleResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserRoleService {
	
	private final UserRoleRepo userRoleRepo;
	private final UserRepo userRepo;
	private final UserRoleMapper userRoleMapper;
	
	private Supplier<UserDetails> authenticatedUser = () -> (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public CreateUserRoleResult createUserRole(UserRoleDto userRoleDto) {
		
		Optional<User> user = userRepo.findByUsername(authenticatedUser.get().getUsername());
		List<UserRole> userRoles = user.get().getRoles();
		
		UserRole newUserRole = userRoleMapper.userRoleDtoToUserRoleEntity(userRoleDto);
		
		boolean userRoleAleadyExists = userRoles.contains(newUserRole);
		
		if (userRoleAleadyExists) {
			return CreateUserRoleResult.userRoleAlreadyApplied();
		}
		
		newUserRole.setUser(user.get());
		userRoleRepo.save(newUserRole);
		return CreateUserRoleResult.createUserRoleSuccess();
	}
}
