package com.example.sis.result;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteUserResult {

	public enum Status {
		DELETE_USER_SUCCESS, USER_NOT_FOUND;
	}

	private Status status;
	private String message;

	public static DeleteUserResult success() {
		return DeleteUserResult.builder()
				.status(Status.DELETE_USER_SUCCESS)
				.message("User successfully deleted")
				.build();
	}

	public static DeleteUserResult userNotFound() {
		return DeleteUserResult.builder()
				.status(Status.USER_NOT_FOUND)
				.message("User not found")
				.build();
	}

}
