package com.example.sis.result.address;

import com.example.sis.dto.AddressDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateAddressResult {
	

	public enum Status {
		UPDATE_ADDRESS_SUCCESS,
		USER_ADDRESS_NOT_SET;
	}
	
	private String message;
	private Status status;
	private AddressDto data;
	
	public static UpdateAddressResult updateAddressSuccess(AddressDto addressDto) {
		return UpdateAddressResult.builder()
				.message("Update Address Success")
				.status(Status.UPDATE_ADDRESS_SUCCESS)
				.data(addressDto)
				.build();
	}
	
	public static UpdateAddressResult userAddressNotSet() {
		return UpdateAddressResult.builder()
				.message("User Address Not Set")
				.status(Status.USER_ADDRESS_NOT_SET)
				.build();
	}
}
