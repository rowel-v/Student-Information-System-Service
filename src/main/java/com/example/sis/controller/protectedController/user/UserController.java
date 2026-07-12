package com.example.sis.controller.protectedController.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.UserDto;
import com.example.sis.dto.request.UpdateUserRequest;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.DeleteUserResult;
import com.example.sis.result.GetUserResult;
import com.example.sis.result.UpdateUserResult;
import com.example.sis.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int userId) {
		DeleteUserResult result = userService.deleteUser(userId);

		return switch (result.getStatus()) {
		case USER_NOT_FOUND -> ResponseEntity.status(404).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(404)
				.build());
		case DELETE_USER_SUCCESS -> ResponseEntity.ok(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(200)
				.build());
		};
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(userService.getAllUser());
	}

	@GetMapping("/user")
	public ResponseEntity<ApiResponse<UserDto>> getUser() {
		GetUserResult result = userService.getUser();

		return switch (result.getStatus()) {
		case USER_NOT_FOUND -> ResponseEntity.status(404).body(ApiResponse.<UserDto>builder()
				.message(result.getMessage())
				.statusCode(404)
				.build());
		case SUCCESS -> ResponseEntity.ok(ApiResponse.<UserDto>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getUserDto())
				.build());
		};
	}

	@PutMapping("/user")
	public ResponseEntity<ApiResponse<UserDto>> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
		UpdateUserResult result = userService.updateUser(updateUserRequest);

		return switch (result.getStatus()) {
		case USER_NOT_EXISTS -> ResponseEntity.status(404).body(ApiResponse.<UserDto>builder()
				.message(result.getMessage())
				.statusCode(404)
				.build());
		case UPDATE_USER_SUCCESS -> ResponseEntity.ok(ApiResponse.<UserDto>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getUpdatedUserDto())
				.build());
		};
	}
}
