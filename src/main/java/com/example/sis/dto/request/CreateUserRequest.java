package com.example.sis.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.sis.dto.AddressDto;

import lombok.Value;

@Value
public class CreateUserRequest {
	
	String username;
	String password;
	
	String firstname;
	String middlename;
	String lastname;
	LocalDate birthDate;
	
	List<String> roles;
	
	List<AddressDto> addresses;

}
