package com.example.sis.dto.request.publicRequest;

import lombok.Value;

@Value
public class LoginUserAccountRequest {
	
	private String username;
	private String password;

}
