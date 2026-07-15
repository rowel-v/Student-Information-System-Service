package com.example.sis.result.address;

import com.example.sis.dto.AddressDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAddressResult {
	
	public enum Status {
		SAVE_ADRESS_SUCCESS,
		USER_ALREADY_SET_ADDRESS;
	}
	
	private String message;
	private Status status;
	private AddressDto data;
	
	public static CreateAddressResult saveAddressSuccess(AddressDto addressDto) {
		return CreateAddressResult.builder()
				.message("Address Save Success")
				.status(Status.SAVE_ADRESS_SUCCESS)
				.data(addressDto)
				.build();
	}
	
	public static CreateAddressResult userAlreadySetAddress() {
		return CreateAddressResult.builder()
				.message("User Already Set Address")
				.status(Status.USER_ALREADY_SET_ADDRESS)
				.build();
	}

}
