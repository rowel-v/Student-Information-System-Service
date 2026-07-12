package com.example.sis;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class Main {

	public static void main(String[] args) {
		
		String jwt = generateToken("rowel123");
		
		System.out.println(isExpired(jwt));
		
	}
	
	static String myKey = "Secret_key_1002203203203203203023023023023023023023023020203203203";

	static SecretKey mySecretKey = Keys.hmacShaKeyFor(myKey.getBytes());
	
	
	public static String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
				.signWith(mySecretKey)
				.id("2")
				.claim("role", "USER")
				.claim("role", "")
				.compact();
	}
	
	static String extractUsername(String jwt) {
		return Jwts.parser()
				.verifyWith(mySecretKey)
				.build()
				.parseSignedClaims(jwt)
				.getPayload()
				.getSubject();
	}
	
	static boolean isExpired(String jwt) {
		Date expiration = Jwts.parser()
				.verifyWith(mySecretKey)
				.build()
				.parseSignedClaims(jwt)
				.getPayload()
				.getExpiration();
		Date now = Date.from(Instant.now());
		return expiration.before(now);
	}

}
