package com.example.sis.dto.request.publicRequest;

import lombok.Value;

@Value
public class LoginUserRequest {
	
	private String username;
	private String password;

}
