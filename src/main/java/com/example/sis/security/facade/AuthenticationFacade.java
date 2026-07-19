package com.example.sis.security.facade;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationFacade {
	
	UserDetails getAuthenticatedUser();
	
	String getUsername();

}
