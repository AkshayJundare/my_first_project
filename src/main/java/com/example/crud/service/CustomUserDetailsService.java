package com.example.crud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import com.example.crud.User.Users;
import com.example.crud.repository.UserRepo;
import com.example.crud.controller.UserController;
import com.example.crud.entity.Users;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService implements UserDetailsService {

	
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	@Autowired
	private UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("inside the CustomerDetailsService: {}", username);

		
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		logger.info("love : {},{}", user.getUsername(),user.getPassword());
		Set<GrantedAuthority> authorities = Set.of(); // empty set since no roles

		return new org.springframework.security.core.userdetails.User(
		        user.getUsername(),
		        user.getPassword(),
		        authorities
		);
	}

	
}