package com.example.sis.result;

import com.example.sis.dto.UserDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserResult {
	
	public enum Status {
		UPDATE_USER_SUCCESS, USER_NOT_EXISTS;
	}
	
	private Status status;
	private String message;
	private UserDto updatedUserDto;
	
	public static UpdateUserResult usetNotFound() {
		return UpdateUserResult.builder()
				.status(Status.USER_NOT_EXISTS)
				.message("User Not Found")
				.build();
	}
	
	public static UpdateUserResult success(UserDto updatedUserDto) {
		return UpdateUserResult.builder()
				.status(Status.UPDATE_USER_SUCCESS)
				.message("Update User Success")
				.updatedUserDto(updatedUserDto)
				.build();
	}

}
