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
	
	public static CreateUserRoleResult userRoleAlreadyApplied() {
		return CreateUserRoleResult.builder()
				.status(Status.USER_ROLE_ALREADY_APPLIED)
				.message(null)
				.build();
	}
	
	public static CreateUserRoleResult createUserRoleSuccess() {
		return CreateUserRoleResult.builder()
				.status(Status.CREATE_USER_ROLE_SUCCESS)
				.message(null)
				.build();
	}

}
