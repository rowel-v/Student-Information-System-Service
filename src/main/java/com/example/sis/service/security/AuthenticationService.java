package com.example.sis.service.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sis.dto.request.publicRequest.CreateUserAccountRequest;
import com.example.sis.dto.request.publicRequest.LoginUserAccountRequest;
import com.example.sis.entity.User;
import com.example.sis.mapper.UserMapper;
import com.example.sis.repository.UserRepo;
import com.example.sis.result.CreateUserAccountResult;
import com.example.sis.result.LoginUserResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	private final UserRepo userRepo;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public LoginUserResult loginUserRequest(LoginUserAccountRequest loginUserRequest) {

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
	
	@SuppressWarnings("unused")
	public CreateUserAccountResult createUserAccount(CreateUserAccountRequest req) {

		String username = req.getUsername();

		return userRepo.findByUsername(username)
				.map(user -> CreateUserAccountResult.userAlreadyExists())
				.orElseGet(() -> {
					User newUser = userMapper.createUserAccountToUserEntity(req);
					newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
					userRepo.save(newUser);
					return CreateUserAccountResult.createAccountSuccess();
				});
	}
}
