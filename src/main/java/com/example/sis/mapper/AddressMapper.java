package com.example.sis.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.sis.dto.AddressDto;
import com.example.sis.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	Address addressDtoToAddressEntity(AddressDto addressDto);
	
	AddressDto addressEntityToAddressDto(Address address);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateAddressEntity(AddressDto addressDto, @MappingTarget Address address);
}
