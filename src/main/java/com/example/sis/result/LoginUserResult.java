package com.example.sis.result;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUserResult {
	
	public enum Result {
		AUTHORIZED, UNAUTHORIZED;
	}
	
	private Result status;
	private String message;
	private String jwt;
	
	public static LoginUserResult failed() {
		return LoginUserResult.builder()
				.status(Result.UNAUTHORIZED)
				.message("Login failed")
				.build();
	}
	
	public static LoginUserResult success(String jwt) {
		return LoginUserResult.builder()
				.status(Result.AUTHORIZED)
				.message("Login success")
				.jwt(jwt)
				.build();
	}
	
}
