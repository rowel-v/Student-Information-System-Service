package com.example.sis.result.userRole;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteUserRoleResult {
	
	public enum Status {
		DELETE_USER_ROLE_SUCCESS,
		USER_ROLE_NOT_EXISTS
	}
	
	private Status status;
	private String message;
	
	public static DeleteUserRoleResult deleteUserRoleSuccess() {
		return DeleteUserRoleResult.builder()
				.message("User Role Success Delete")
				.status(Status.DELETE_USER_ROLE_SUCCESS)
				.build();
	}
	
	public static DeleteUserRoleResult userRoleNotExists(String role) {
		return DeleteUserRoleResult.builder()
				.message("User Role [" + role.toUpperCase() + "] Not Exists")
				.status(Status.USER_ROLE_NOT_EXISTS)
				.build();
	}
}
