package com.example.sis.result.userRole;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserRoleResult {
	
	private String message;
	
	private List<String> userRole;
	
	public static GetUserRoleResult create(List<String> userRole) {
		return GetUserRoleResult.builder()
				.message("Here are the User Roles")
				.userRole(userRole)
				.build();
	}
}
