package com.example.sis.controller.publicController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.request.CreateUserRequest;
import com.example.sis.dto.request.LoginUserRequest;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.CreateUserResult;
import com.example.sis.result.LoginUserResult;
import com.example.sis.service.security.AuthenticationService;
import com.example.sis.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("userGatewaytController")
public class UserController {

	private final UserService userService;
	private final AuthenticationService authenService;

	@PostMapping("/auth/login")
	public ResponseEntity<ApiResponse<String>> loginUserRequest(@RequestBody LoginUserRequest req) {

		LoginUserResult result = authenService.loginRequest(req);

		return switch (result.getStatus()) {
		case UNAUTHORIZED -> ResponseEntity.status(401).body(ApiResponse.<String>builder()
				.message(result.getMessage())
				.statusCode(401)
				.build());
		case AUTHORIZED -> ResponseEntity.ok(ApiResponse.<String>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getJwt())
				.build());
		};
	}

	@PostMapping("/user")
	public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody CreateUserRequest createUserDto) {

		CreateUserResult result = userService.createUser(createUserDto);

		return switch (result.getStatus()) {
		case USER_ALREADY_EXISTS -> ResponseEntity.status(409).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(409)
				.build());
		case CREATE_USER_SUCCESS -> ResponseEntity.status(204).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(204)
				.build());
		};

	}
}