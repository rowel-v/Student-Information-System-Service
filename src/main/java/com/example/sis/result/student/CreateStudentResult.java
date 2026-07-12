package com.example.sis.result.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStudentResult {
	
	public enum Status {
		SUCCESS, STUDENT_NUMBER_ALREADY_EXISTS;
	}
	
	private String message;
	private Status status;
	
	public static CreateStudentResult sucess() {
		return CreateStudentResult.builder()
				.status(Status.SUCCESS)
				.message("Student Create Success")
				.build();
	}
	
	public static CreateStudentResult studNumberAlreadyExists() {
		return CreateStudentResult.builder()
				.status(Status.STUDENT_NUMBER_ALREADY_EXISTS)
				.message("Student Number Already Exists")
				.build();
	}
}
