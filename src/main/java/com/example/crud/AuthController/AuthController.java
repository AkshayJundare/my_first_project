package com.example.crud.AuthController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.crud.JwtUtil.JwtUtil;
import com.example.crud.controller.UserController;
import com.example.crud.dto.AuthRequest;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

	private static  final Logger logger =LoggerFactory.getLogger(AuthController.class);
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createToken(@RequestBody AuthRequest request,HttpServletRequest httpRequest) {
        
		String clientId = httpRequest.getHeader("client-id");
    	String source = httpRequest.getHeader("request-source");
		try {
            // Attempt authentication
   
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	        String plainPassword = "password123";
	        String hashedPassword = encoder.encode(plainPassword);

	        System.out.println(hashedPassword);
        	
        	
        	logger.info("clientId and  source:{},{}",clientId,source);
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Wrong username or password
        	
        	  return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                      .header("client-id", clientId)
                      .header("request-source", source)
                      .header("X-Auth-Error", "INVALID_CREDENTIALS")
                      .body("Invalid username or password");
        }

        // Load user details (from CustomUserDetailsService)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(token);
    }
	
}
