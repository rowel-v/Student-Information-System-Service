package com.example.sis.service.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {
	
	@PostConstruct
	private void init() {
		SECRET_KEY = Keys.hmacShaKeyFor(s.getBytes());
		System.out.println(s);
		System.err.println(generateToken("rowel123"));
	}
	
	@Value("${jwt.secretkey}")
	private String s;
	private SecretKey SECRET_KEY;
	
	private final Duration EXPIRATION_TIME = Duration.of(1, ChronoUnit.HOURS);
	private Date expiration = Date.from(Instant.now().plus(EXPIRATION_TIME));
	
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(Date.from(Instant.now()))
				.expiration(expiration)
				.signWith(SECRET_KEY)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }

}
