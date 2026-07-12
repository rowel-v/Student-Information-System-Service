package com.example.sis.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sis.dto.UserDto;
import com.example.sis.dto.request.CreateUserRequest;
import com.example.sis.dto.request.UpdateUserRequest;
import com.example.sis.entity.User;
import com.example.sis.mapper.UserMapper;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.CreateUserResult;
import com.example.sis.result.DeleteUserResult;
import com.example.sis.result.GetUserResult;
import com.example.sis.result.UpdateUserResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepo userRepo;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	private Supplier<UserDetails> authenticatedUser = () -> (UserDetails) SecurityContextHolder.getContext()
		.getAuthentication().getPrincipal();
	
	public CreateUserResult createUser(CreateUserRequest createUserRequest) {

		Optional<User> user = userRepo.findByUsername(createUserRequest.getUsername());

		if (user.isPresent()) {
			return CreateUserResult.userAlreadyExists();
		}

		User newUser = userMapper.userDtoToUserEntity(createUserRequest);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		userRepo.save(newUser);
		
		return CreateUserResult.success();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public DeleteUserResult deleteUser(int userId) {
		return userRepo.findById(userId)
				.map(user -> {
					userRepo.delete(user);
					return DeleteUserResult.success();
				})
				.orElse(DeleteUserResult.userNotFound());
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserDto> getAllUser() {
		return userRepo.findAll().stream()
				.map(userMapper::userEntityToUserDto)
				.toList();
	}

	public GetUserResult getUser() {
		return userRepo.findByUsername(authenticatedUser.get().getUsername())
				.map(user -> GetUserResult.success(userMapper.userEntityToUserDto(user)))
				.orElse(GetUserResult.userNotFound());
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UpdateUserResult updateUser(UpdateUserRequest updateUserRequest) {
		return userRepo.findByUsername(authenticatedUser.get().getUsername())
				.map(user -> {
					userMapper.updateUserEntity(updateUserRequest, user);
					userRepo.save(user);
					return UpdateUserResult.success(userMapper.userEntityToUserDto(user));
				})
				.orElse(UpdateUserResult.usetNotFound());
	}






}
