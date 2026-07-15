package com.example.sis.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Value;

@Value
public class UserDto {
	
	String firstname;
	String middlename;
	String lastname;
	
	LocalDate birthDate;
	
	List<String> roles;
	
	AddressDto addresses;
	
}
