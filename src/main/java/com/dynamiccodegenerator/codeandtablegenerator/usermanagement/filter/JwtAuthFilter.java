package com.dynamiccodegenerator.codeandtablegenerator.usermanagement.filter;



import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.modal.JwtBlockListRepository;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service.JwtService;
import com.dynamiccodegenerator.codeandtablegenerator.usermanagement.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// This class helps us to validate the generated jwt token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoService userDetailsService;
	
	@Autowired
	private JwtBlockListRepository jwtBlockListRepository;

	private String getCookieValue(HttpServletRequest req, String cookieName) {
	    return Arrays.stream(req.getCookies())
	            .filter(c -> c.getName().equals(cookieName))
	            .findFirst()
	            .map(Cookie::getValue)
	            .orElse(null);
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = getCookieValue(request, "jwt");
		System.out.println("jwt " + authHeader);
				//request.getHeader("Authorization");
		String token = null;
		String username = null;
		if (authHeader != null && jwtBlockListRepository.findByJwt(authHeader) == null ) {  // && authHeader.startsWith("Bearer ")
			token = authHeader; //.substring(7);
			username = jwtService.extractUsername(token);
			System.out.println("username " + username);

		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtService.validateToken(token, userDetails)) {
				System.out.println("vlidation success ");

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}
