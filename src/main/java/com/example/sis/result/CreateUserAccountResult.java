package com.example.sis.result;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserAccountResult {

	public enum Status {
		CREATE_ACCOUNT_SUCCESS,
		USER_ALREADY_EXISTS
	}

	private Status status;
	private String message;

	public static CreateUserAccountResult createAccountSuccess() {
		return CreateUserAccountResult.builder()
				.status(Status.CREATE_ACCOUNT_SUCCESS)
				.message("User Account Create Success")
				.build();
	}

	public static CreateUserAccountResult userAlreadyExists() {
		return CreateUserAccountResult.builder()
				.status(Status.USER_ALREADY_EXISTS)
				.message("User Already Exists")
				.build();
	}

}
