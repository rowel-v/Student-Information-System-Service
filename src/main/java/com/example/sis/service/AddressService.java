package com.example.sis.service;

import org.springframework.stereotype.Service;

import com.example.sis.dto.AddressDto;
import com.example.sis.entity.Address;
import com.example.sis.mapper.AddressMapper;
import com.example.sis.repository.AddressRepo;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.address.CreateAddressResult;
import com.example.sis.result.address.DeleteAddressResult;
import com.example.sis.result.address.GetAddressResult;
import com.example.sis.result.address.UpdateAddressResult;
import com.example.sis.security.facade.AuthenticationFacade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService {

	private final AddressRepo addressRepo;
	private final AddressMapper addressMapper;

	private final UserRepo userRepo;
	
	private final AuthenticationFacade authenticationFacade;
	
	public GetAddressResult getAddress() {
		log.info("User Request to Get Address");
		return userRepo.findByUsername(authenticationFacade.getUsername())
				.map(user -> {
					
					if (user.getAddresses() == null) {
						log.warn("User Has No Address Saved");
						return GetAddressResult.userAddressNotSet();
					}
					
					AddressDto userAddress = addressMapper.addressEntityToAddressDto(user.getAddresses());
					return GetAddressResult.getAddressSuccess(userAddress);
				})
				.orElseThrow();
	}

	public CreateAddressResult saveAddress(AddressDto addressDto) {

		Address address = addressMapper.addressDtoToAddressEntity(addressDto);

		return userRepo.findByUsername(authenticationFacade.getUsername())
				.map(user -> {

					if (user.getAddresses() != null) {
						return CreateAddressResult.userAlreadySetAddress();
					}

					address.setUser(user);
					addressRepo.save(address);
					AddressDto savedAddress = addressMapper.addressEntityToAddressDto(address);
					return CreateAddressResult.saveAddressSuccess(savedAddress);
				})
				.orElseThrow();
	}
	
	public DeleteAddressResult deleteAddress() {
		return userRepo.findByUsername(authenticationFacade.getUsername())
				.map(user -> {
					
					if (user.getAddresses() == null) {
						return DeleteAddressResult.userAddressNotSet();
					}
					
					user.setAddresses(null);
					userRepo.save(user);
					return DeleteAddressResult.deleteAddressSuccess();
				})
				.orElseThrow();
	}
	
	public UpdateAddressResult updateAddress(AddressDto addressDto) {
		
		return userRepo.findByUsername(authenticationFacade.getUsername())
				.map(user -> {
				
					if (user.getAddresses() == null) {
						return UpdateAddressResult.userAddressNotSet();
					}
				
					Address userAddress = user.getAddresses();
					addressMapper.updateAddressEntity(addressDto, userAddress);
					userRepo.save(user);
					AddressDto savedAddress = addressMapper.addressEntityToAddressDto(user.getAddresses());
					return UpdateAddressResult.updateAddressSuccess(savedAddress);
				})
				.orElseThrow();
	}

}
