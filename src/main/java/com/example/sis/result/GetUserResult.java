package com.example.sis.result;

import com.example.sis.dto.UserDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserResult {
	
	public enum Status {
		SUCCESS, USER_NOT_FOUND;
	}
	
	private Status status;
	private String message;
	private UserDto userDto;
	
	public static GetUserResult success(UserDto userDto) {
		return GetUserResult.builder()
				.status(Status.SUCCESS)
				.message("User fetch success")
				.userDto(userDto)
				.build();
	}
	
	public static GetUserResult userNotFound() {
		return GetUserResult.builder()
				.status(Status.USER_NOT_FOUND)
				.message("User not found")
				.build();
	}
}
