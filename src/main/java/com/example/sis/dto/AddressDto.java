package com.example.sis.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AddressDto {
	
	String line1;
	String line2;
	String barangay;
	String city;
	String province;
	int postalCode;

}
