package com.example.sis.controller.protectedController.student;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sis.dto.StudentDto;
import com.example.sis.dto.response.ApiResponse;
import com.example.sis.result.student.CreateStudentResult;
import com.example.sis.service.StudentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/student")
@RestController
public class StudentController {
	
	private final StudentService studentService;

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> saveStudent(@RequestBody StudentDto studentDto) {
		
		CreateStudentResult result = studentService.saveStudent(studentDto);
		
		return switch (result.getStatus()) {
		case STUDENT_NUMBER_ALREADY_EXISTS -> ResponseEntity.status(409).body(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(409)
				.build());
		case SUCCESS -> ResponseEntity.ok(ApiResponse.<Void>builder()
				.message(result.getMessage())
				.statusCode(200)
				.build());
		};
		
	}
	
}
