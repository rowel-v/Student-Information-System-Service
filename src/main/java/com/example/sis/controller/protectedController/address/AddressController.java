package com.example.sis.controller.protectedController.address;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.AddressDto;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.address.CreateAddressResult;
import com.example.sis.result.address.DeleteAddressResult;
import com.example.sis.result.address.GetAddressResult;
import com.example.sis.result.address.UpdateAddressResult;
import com.example.sis.service.AddressService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user/address")
@RestController
public class AddressController {

	private final AddressService addressService;
	
	@GetMapping
	ResponseEntity<ApiResponse<AddressDto>> getAddress() {
		GetAddressResult result = addressService.getAddress();
		
		return switch (result.getStatus()) {
		case USER_ADDRESS_NOT_SET -> ResponseEntity.status(400).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(400)
				.build());
		case GET_ADDRESS_SUCCESS -> ResponseEntity.status(200).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getData())
				.build());
		};
	}
	
	@PostMapping
	ResponseEntity<ApiResponse<AddressDto>> saveAddress(@RequestBody AddressDto addressDto) {
		CreateAddressResult result = addressService.saveAddress(addressDto);
		
		return switch (result.getStatus()) {
		case USER_ALREADY_SET_ADDRESS -> ResponseEntity.status(409).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(409)
				.build());
		case SAVE_ADRESS_SUCCESS -> ResponseEntity.status(200).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getData())
				.build());
		};
	}
	
	@DeleteMapping
	ResponseEntity<ApiResponse<Void>> deleteAddress() {
		DeleteAddressResult result = addressService.deleteAddress();
		
		return switch (result.getStatus()) {
		case USER_ADDRESS_NOT_SET -> ResponseEntity.status(400).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(400)
				.build());
		case DELETE_ADDRESS_SUCCESS -> ResponseEntity.status(204).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(204)
				.build());
		};
	}
	
	@PutMapping
	ResponseEntity<ApiResponse<AddressDto>> updateAddress(@RequestBody AddressDto addressDto) {
		UpdateAddressResult result = addressService.updateAddress(addressDto);
		
		return switch (result.getStatus()) {
		case USER_ADDRESS_NOT_SET -> ResponseEntity.status(400).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(400)
				.build());
		case UPDATE_ADDRESS_SUCCESS -> ResponseEntity.status(200).body(ApiResponse.<AddressDto>builder()
				.message(result.getMessage())
				.statusCode(200)
				.data(result.getData())
				.build());
		};
	}
}
