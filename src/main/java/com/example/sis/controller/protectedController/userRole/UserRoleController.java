package com.example.sis.controller.protectedController.userRole;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.UserRoleDto;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.userRole.CreateUserRoleResult;
import com.example.sis.result.userRole.DeleteUserRoleResult;
import com.example.sis.result.userRole.GetUserRoleResult;
import com.example.sis.service.UserRoleService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/role")
@RequiredArgsConstructor
@RestController
public class UserRoleController {
	
	private final UserRoleService userRoleService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveUserRole(@RequestBody UserRoleDto req) {
		
		CreateUserRoleResult result = userRoleService.createUserRole(req);
		
		return switch (result.getStatus()) {
		case USER_ROLE_ALREADY_APPLIED -> ResponseEntity.status(409).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(409)
				.build());
		case CREATE_USER_ROLE_SUCCESS -> ResponseEntity.status(204).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(204)
				.build());
		};
	}
	
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteUserRole(@RequestBody UserRoleDto req) {
		DeleteUserRoleResult result = userRoleService.deleteUserRole(req);
		
		return switch (result.getStatus()) {
		case USER_ROLE_NOT_EXISTS -> ResponseEntity.status(404).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(404)
				.build());
		case DELETE_USER_ROLE_SUCCESS -> ResponseEntity.status(200).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(200)
				.build());
		};
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<String>>> getUserRole() {
		GetUserRoleResult result = userRoleService.getUserRole();
		return ResponseEntity.ok(ApiResponse.<List<String>>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getUserRole())
				.build());
	}

}
