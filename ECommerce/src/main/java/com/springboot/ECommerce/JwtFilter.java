package com.springboot.ECommerce;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.ECommerce.service.CustomUserDetailsService;
import com.springboot.ECommerce.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	 @Autowired
	 private JwtUtil jwtUtil;
	 @Autowired
	 private CustomUserDetailsService customUserDetailsService;
	 
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String username = null;
		     String jwt = null;
		     
			/* Check if Authorization header is present and fetch the token */
			final String authorizationHeader = request.getHeader("Authorization");
			
			
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            username = jwtUtil.extractUsername(jwt);
	        }
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				// check username is exits in db or not
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				
				
				boolean isValid = jwtUtil.verifyToken(jwt, username); 
				if(isValid) {
					// log user can use apis
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
		                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken
		                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);
		}
		catch(Exception e) {
			 System.out.println(e);
		}
			
	}
	
	 
}