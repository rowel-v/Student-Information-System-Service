package com.example.sis.result.userRole;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserRoleResult {
	
	public enum Status {
		CREATE_USER_ROLE_SUCCESS,
		USER_ROLE_ALREADY_APPLIED
	}
	
	private Status status;
	private String message;
	
	public static CreateUserRoleResult userRoleAlreadyApplied(String role) {
		return CreateUserRoleResult.builder()
				.status(Status.USER_ROLE_ALREADY_APPLIED)
				.message("User role [" + role.toUpperCase() + "] already applied")
				.build();
	}
	
	public static CreateUserRoleResult createUserRoleSuccess() {
		return CreateUserRoleResult.builder()
				.status(Status.CREATE_USER_ROLE_SUCCESS)
				.message("User role created success")
				.build();
	}

}
