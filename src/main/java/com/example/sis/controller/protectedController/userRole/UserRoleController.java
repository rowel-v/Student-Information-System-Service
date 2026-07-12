package com.example.sis.controller.protectedController.userRole;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.UserRoleDto;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.userRole.CreateUserRoleResult;
import com.example.sis.service.UserRoleService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/role")
@RequiredArgsConstructor
@RestController
public class UserRoleController {
	
	private final UserRoleService userRoleService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveUserRole(UserRoleDto req) {
		
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

}
