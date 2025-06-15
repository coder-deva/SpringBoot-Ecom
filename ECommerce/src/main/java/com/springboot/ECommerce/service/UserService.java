package com.springboot.ECommerce.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.UserRepository;





@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User signUp(User user) {
		
		
		String plainPassword = user.getPassword(); //plaintext
		String encodedPassword =  passwordEncoder.encode(plainPassword);//this will encrypt the password
		user.setPassword(encodedPassword); 
		
		return userRepository.save(user);
	}
	
	public User findByUsername(String username) {
	    return userRepository.getByUsername(username);
	}


	
	
}
