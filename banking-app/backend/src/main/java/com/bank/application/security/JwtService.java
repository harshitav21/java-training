package com.bank.application.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.bank.application.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SECRET = "my-super-secret-key-my-super-secret-key";
	private static final long EXPIRATION = 1000 * 60 * 60; // 1 hour

	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	// Generate Token
	public String generateToken(User user) {
		return Jwts.builder().setSubject(user.getUsername()).claim("role", user.getRole().name())
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	// Extract Username
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	// Validate Token
	public boolean isTokenValid(String token, User user) {
		String username = extractUsername(token);
		return username.equals(user.getUsername()) && !extractAllClaims(token).getExpiration().before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
