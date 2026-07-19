package com.example.sis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.sis.dto.request.publicRequest.CreateUserAccountRequest;
import com.example.sis.dto.request.publicRequest.LoginUserAccountRequest;
import com.example.sis.mapper.UserMapper;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.CreateUserAccountResult;
import com.example.sis.result.LoginUserResult;
import com.example.sis.service.security.AuthenticationService;
import com.example.sis.service.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class AutheticationServiceTest {

	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtService jwtService;
	@Mock private UserRepo userRepo;
	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	@Mock
	private PasswordEncoder passwordEncoder;

	private AuthenticationService authenticationService;

	@BeforeEach
	void setUp() {	
		authenticationService = new AuthenticationService(
				authenticationManager, jwtService, userRepo, userMapper, passwordEncoder);
	}

	@Test
	void loginUser_CredentialMatch_SuccessGenerateJwt() {
		
		LoginUserAccountRequest req = new LoginUserAccountRequest("rowel123", "password123");
		
		UserDetails user = User.builder()
				.username(req.getUsername())
				.password(req.getPassword())
				.authorities("STUDENT")
				.build();
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());		
		
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
		.thenReturn(auth);
		
		when(jwtService.generateToken("rowel123")).thenReturn("ey.jwt.token");
		
		LoginUserResult result = authenticationService.loginUserRequest(req);
		
		assertNotNull(result);
		assertEquals(LoginUserResult.Result.AUTHORIZED, result.getStatus());
		assertEquals("ey.jwt.token", result.getJwt());
		
		verify(authenticationManager, times(1)).authenticate(any());
		verify(jwtService, times(1)).generateToken(any());
	}
	
	@Test
	void loginUser_CredentialNotMatch_ResultLoginFailed() {
		
		LoginUserAccountRequest req = new LoginUserAccountRequest("rowel123", "PASSWORDD123");
		
		when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
		
		LoginUserResult result = authenticationService.loginUserRequest(req);
		
		assertNotNull(result);
		assertEquals(LoginUserResult.Result.UNAUTHORIZED, result.getStatus());
		assertNull(result.getJwt());
		
		verify(authenticationManager, times(1)).authenticate(any());
		verify(jwtService, times(0)).generateToken(anyString());
		
	}
	
	@Test
	void createUserAccount_AccountNotExist_SuccessfullyCreated() {
		
		when(userRepo.findByUsername(any())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(any())).thenReturn("ENCODED PW");
		
		CreateUserAccountRequest req = new CreateUserAccountRequest("rowel123", "password123");
		CreateUserAccountResult result = authenticationService.createUserAccount(req);
		
		assertNotNull(result);
		assertEquals(CreateUserAccountResult.Status.CREATE_ACCOUNT_SUCCESS, result.getStatus());
		
		verify(userRepo, times(1)).save(any());
		verify(passwordEncoder, times(1)).encode(any());
	}
	
	@Test
	void createUserAccount_AccountExist_NotCreated() {
		
		com.example.sis.entity.User user = com.example.sis.entity.User.builder()
				.username("rowel123")
				.password("password123")
				.build();
		
		when(userRepo.findByUsername(any())).thenReturn(Optional.of(user));
		
		CreateUserAccountRequest req = new CreateUserAccountRequest("rowel123", "password123");
		CreateUserAccountResult result = authenticationService.createUserAccount(req);
		
		assertNotNull(result);
		assertEquals(CreateUserAccountResult.Status.USER_ALREADY_EXISTS, result.getStatus());
		
		verify(userRepo, times(0)).save(any());
		verify(passwordEncoder, times(0)).encode(any());
	}
}
