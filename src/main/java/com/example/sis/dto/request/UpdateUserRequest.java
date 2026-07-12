package com.example.sis.dto.request;

import java.time.LocalDate;

import lombok.Value;

@Value
public class UpdateUserRequest {
	
	String firstname;
	String middlename;
	String lastname;
	LocalDate birthDate;

}
