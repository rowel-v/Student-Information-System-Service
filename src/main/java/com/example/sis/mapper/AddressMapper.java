package com.example.sis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sis.dto.request.CreateUserRequest;
import com.example.sis.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	//@Mapping(target = "line1", source = "addresses.line1")
	Address toEntity(CreateUserRequest createUserDto);
//	apped target properties: "line1, line2, barangay, city, province, postalCode"
	

}
