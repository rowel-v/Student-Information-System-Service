package com.example.sis.dto.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ApiResponse<T> {
	
	private String message;
	private int statusCode;
	
	private T data;
}
