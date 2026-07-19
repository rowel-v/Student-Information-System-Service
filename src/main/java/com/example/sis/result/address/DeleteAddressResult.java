package com.example.sis.result.address;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeleteAddressResult {
	
	public enum Status {
		DELETE_ADDRESS_SUCCESS,
		USER_ADDRESS_NOT_SET;
	}
	
	private String message;
	private Status status;
	
	public static DeleteAddressResult deleteAddressSuccess() {
		return DeleteAddressResult.builder()
				.message("Delete Address Success")
				.status(Status.DELETE_ADDRESS_SUCCESS)
				.build();
	}
	
	public static DeleteAddressResult userAddressNotSet() {
		return DeleteAddressResult.builder()
				.message("User Address Not Set")
				.status(Status.USER_ADDRESS_NOT_SET)
				.build();
	}
}
