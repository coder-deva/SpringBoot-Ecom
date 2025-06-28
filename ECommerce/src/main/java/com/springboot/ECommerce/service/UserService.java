package com.springboot.ECommerce.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.repository.SellerRepository;
import com.springboot.ECommerce.repository.UserRepository;






@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;
	private SellerRepository sellerRepository;
	private AdminRepository adminRepository;
	
	
	

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			CustomerRepository customerRepository, SellerRepository sellerRepository, AdminRepository adminRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
		this.sellerRepository = sellerRepository;
		this.adminRepository = adminRepository;
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
	
	public Object getUserInfo(String username) {
		User user = userRepository.findByUsername(username);
		switch (user.getRole()) {
		
			case ROLE_CUSTOMER:
				Customer customer =customerRepository.getCustomerByUsername(username);
				return customer;
				
			case ROLE_SELLER:
				Seller seller =	sellerRepository.getSellerByUsername(username);
				
		
		            return seller;
				
		
				
			case ROLE_ADMIN:
				Admin admin =	adminRepository.getAdminByUsername(username);
				
				
	            return admin;
			default:
				return null;
		}

	}


	
	
}
