package com.example.sis.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResult {
	
	public enum Status {
		CREATE_USER_SUCCESS, USER_ALREADY_EXISTS
	}
	
	private Status status;
	private String message;
	
	public static CreateUserResult userAlreadyExists() {
		return CreateUserResult.builder()
				.status(Status.USER_ALREADY_EXISTS)
				.message("User Already Exists")
				.build();
	}
	
	public static CreateUserResult success() {
		return CreateUserResult.builder()
				.status(Status.CREATE_USER_SUCCESS)
				.message("User Create Success")
				.build();
	}

}
