package com.example.sis.result.address;

import com.example.sis.dto.AddressDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetAddressResult {

	public enum Status {
		GET_ADDRESS_SUCCESS,
		USER_ADDRESS_NOT_SET;
	}

	private String message;
	private Status status;
	private AddressDto data;

	public static GetAddressResult getAddressSuccess(AddressDto addressDto) {
		return GetAddressResult.builder()
				.message("Get Address Success")
				.status(Status.GET_ADDRESS_SUCCESS)
				.data(addressDto)
				.build();
	}

	public static GetAddressResult userAddressNotSet() {
		return GetAddressResult.builder()
				.message("User Address Not Set")
				.status(Status.USER_ADDRESS_NOT_SET)
				.build();
	}

}
