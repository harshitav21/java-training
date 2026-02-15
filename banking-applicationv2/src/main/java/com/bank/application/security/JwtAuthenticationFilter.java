package com.bank.application.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bank.application.entity.User;
import com.bank.application.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserRepository userRepository;

	public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getServletPath();

		// ✅ Skip authentication endpoints completely
		if (path.startsWith("/api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
		    filterChain.doFilter(request, response);
		    return;
		}

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			String token = authHeader.substring(7);
			String username = jwtService.extractUsername(token);

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				User user = userRepository.findByUsername(username).orElse(null);

				if (user != null && jwtService.isTokenValid(token, user)) {

					SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							user.getUsername(), null, Collections.singletonList(authority));

					SecurityContextHolder.getContext().setAuthentication(authToken);
					
					System.out.println("Authenticated user: " + user.getUsername());
					System.out.println("Role from DB: " + user.getRole().name());
					System.out.println("Authority set: ROLE_" + user.getRole().name());

				}
			}

		} catch (ExpiredJwtException ex) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token expired");
			return;

		} catch (JwtException ex) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid token");
			return;
		}
		

		filterChain.doFilter(request, response);
	}

}
