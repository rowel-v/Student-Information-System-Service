package com.example.sis.dto;

import lombok.Value;

@Value
public class AddressDto {
	
	String line1;
	String line2;
	String barangay;
	String city;
	String province;
	int postalCode;

}
