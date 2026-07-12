package com.example.sis.service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sis.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepo userRepo;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		com.example.sis.entity.User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("USER NAME NOT FOUND!!"));
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getRoles()
						.stream()
						.map(u -> new SimpleGrantedAuthority("ROLE_" + u.getRole().toUpperCase()))
						.toList())
				.build();
	}

}
