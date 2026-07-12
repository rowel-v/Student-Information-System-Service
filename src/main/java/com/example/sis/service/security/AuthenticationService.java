package com.example.sis.service.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.sis.dto.request.publicRequest.LoginUserRequest;
import com.example.sis.result.LoginUserResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public LoginUserResult loginUserRequest(LoginUserRequest loginUserRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUserRequest.getUsername(), loginUserRequest.getPassword()));
			UserDetails user = (UserDetails) authentication.getPrincipal();
			LoginUserResult result = LoginUserResult.success(jwtService.generateToken(user.getUsername()));
			return result;
		} catch (BadCredentialsException e) {
			return LoginUserResult.failed();
		}
	}
}
