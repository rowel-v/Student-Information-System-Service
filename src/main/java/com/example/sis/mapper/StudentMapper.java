package com.example.sis.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.sis.dto.StudentDto;
import com.example.sis.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	Student studentDtoToStudentEntity(StudentDto studentDto);

}
