package com.example.sis.service;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.sis.dto.StudentDto;
import com.example.sis.entity.Student;
import com.example.sis.entity.User;
import com.example.sis.mapper.StudentMapper;
import com.example.sis.repository.StudentRepo;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.student.CreateStudentResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
	
	private final StudentRepo studentRepo;
	private final UserRepo userRepo;
	
	private Supplier<UserDetails> authenticatedUser = () -> (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	private final StudentMapper studentMapper;
	
	@PreAuthorize("hasRole('STUDENT')")
	public CreateStudentResult saveStudent(StudentDto studentDto) {
		
		var alreadyExists = studentRepo.findByStudentNumber(studentDto.getStudentNumber()).isPresent();
		
		if (alreadyExists) {
			return CreateStudentResult.studNumberAlreadyExists();
		}
		
		Optional<User> user = userRepo.findByUsername(authenticatedUser.get().getUsername());
		
		Student student = studentMapper.studentDtoToStudentEntity(studentDto);
		student.setUser(user.get());
		studentRepo.save(student);
		return CreateStudentResult.sucess();
	}

}
