package com.example.sis.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sis.dto.AddressDto;
import com.example.sis.entity.Address;
import com.example.sis.entity.User;
import com.example.sis.mapper.AddressMapper;
import com.example.sis.repository.AddressRepo;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.address.CreateAddressResult;
import com.example.sis.result.address.GetAddressResult;
import com.example.sis.security.facade.AuthenticationFacade;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@Mock private UserRepo userRepo;
	@Mock private AddressRepo addressRepo;
	@Mock private AuthenticationFacade authenticationFacade;
	private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
	private AddressService addressService;

	@BeforeEach
	void setUp() {
		addressService = new AddressService(addressRepo, addressMapper, userRepo, authenticationFacade);
	}

	@Test
	void userAddress_IsSet_ReturnSuccess() {

		Address userAddress = Address.builder()
				.line1("address line 1")
				.barangay("san roque")
				.city("Antipolo")
				.postalCode(1870)
				.build();

		User user = User.builder()
				.username("rowel123")
				.password("rowelPassword123")
				.addresses(userAddress)
				.build();

		when(userRepo.findByUsername(user.getUsername()))
		.thenReturn(Optional.of(user));
		
		when(authenticationFacade.getUsername()).thenReturn(user.getUsername());
		
		GetAddressResult result = addressService.getAddress();

		assertNotNull(result);
		assertEquals(GetAddressResult.Status.GET_ADDRESS_SUCCESS, result.getStatus());
		assertEquals("Antipolo", result.getData().getCity());
		assertEquals("san roque", result.getData().getBarangay());
		assertEquals(1870, result.getData().getPostalCode());
		
		verify(authenticationFacade, times(1)).getUsername();
	}
	
	@Test
	void userAddress_IsNotSetYeT_ReturnUserAddressNotSet() {
		
		User user = User.builder()
				.username("rowel123")
				.password("password123")
				.build();
		
		when(authenticationFacade.getUsername()).thenReturn("rowel123");
		when(userRepo.findByUsername("rowel123")).thenReturn(Optional.of(user));
		
		GetAddressResult result = addressService.getAddress();
		
		assertNotNull(result);
		assertEquals(GetAddressResult.Status.USER_ADDRESS_NOT_SET, result.getStatus());
		assertNull(result.getData());
		
		verify(userRepo, times(1)).findByUsername(anyString());
		verify(authenticationFacade, times(1)).getUsername();
	}
	
	@Test
	void userAddress_UserNotExists_ThrowsNoSuchElementException() {
		
		when(userRepo.findByUsername(any())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> addressService.getAddress())
		.isInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	void createUserAddress_AddressNotSet_SuccessfullyCreated() {
		
		AddressDto addressDto = AddressDto.builder().line1("Line 1").city("City").postalCode(1870).build();
		
		User user = User.builder().username("rowel123").password("password123").build();
		
		when(authenticationFacade.getUsername()).thenReturn("rowel123");
		when(userRepo.findByUsername("rowel123")).thenReturn(Optional.of(user));
		when(addressRepo.save(any())).thenReturn(addressMapper.addressDtoToAddressEntity(addressDto));
		
		CreateAddressResult result = addressService.saveAddress(addressDto);
		
		verify(addressRepo, times(1)).save(any());
		
		assertNotNull(result);
		assertEquals(CreateAddressResult.Status.SAVE_ADRESS_SUCCESS, result.getStatus());
		assertNotNull(result.getData());
	}
	
	@Test
	void createUserAddress_AddressSet_ReturnUserAlreadySetAddress() {
		
		Address address = Address.builder().line1("Sample").barangay("Brgy").build();
		User user = User.builder().username("rowel123").password("password123").addresses(address).build();
		
		when(authenticationFacade.getUsername()).thenReturn("rowel123");
		when(userRepo.findByUsername("rowel123")).thenReturn(Optional.of(user));
		
		AddressDto addressDto  = AddressDto.builder().barangay("sample").line1("sample").build();
		CreateAddressResult result = addressService.saveAddress(addressDto);
		
		assertNotNull(result);
		assertEquals(CreateAddressResult.Status.USER_ALREADY_SET_ADDRESS, result.getStatus());
		
		verify(addressRepo, times(0)).save(any());
	}

}
