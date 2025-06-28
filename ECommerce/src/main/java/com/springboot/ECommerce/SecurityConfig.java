package com.springboot.ECommerce;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
/*
	@Bean
	UserDetailsService users() {  	//<- In memory Authentication
		PasswordEncoder encoder = passwordEncoder();
		
		UserDetails user = User.builder()
		        .username("user")
		        .password(encoder.encode("user123")) // encode password
		        .roles("USER")
		        .build();
		    
		    UserDetails admin = User.builder()
		        .username("admin")
		        .password(encoder.encode("admin123")) // encode password
		        .roles("ADMIN")
		        .build();
		    
		return new InMemoryUserDetailsManager(user, admin);
	}
	
*/

	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable()) 
			
			.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					
					  // Public endpoints
					.requestMatchers("/api/user/signup").permitAll() 
					.requestMatchers("/api/user/token").permitAll() 
					.requestMatchers("/api/user/details").permitAll()
					
					.requestMatchers("/api/admin/add").hasAuthority("ROLE_ADMIN")
					
					
					
					 .requestMatchers("/api/seller/add").hasAuthority("ROLE_ADMIN") // pass
					 .requestMatchers("/api/seller/profile").hasAuthority("ROLE_SELLER") //pass
					 .requestMatchers("/api/seller/update/{id}").hasAuthority("ROLE_SELLER") // pass
					 .requestMatchers("/api/seller/upload/profile-pic").hasAuthority("ROLE_SELLER") // pass
			        .requestMatchers("/api/product/all").permitAll() // pass
			        
			        // Seller role access
			        .requestMatchers("/api/product/add").hasAuthority("ROLE_SELLER") // pass
			        .requestMatchers("/api/product/update/{id}").hasAuthority("ROLE_SELLER")
			        .requestMatchers("/api/product/delete/{id}").hasAuthority("ROLE_SELLER")
			      
					
			   .requestMatchers("/api/customer/register").permitAll() // pass
			   .requestMatchers("/api/customer/all").permitAll() // pass
			   .requestMatchers("/api/customer/profile").hasAuthority("ROLE_CUSTOMER") //pass
				 .requestMatchers("/api/customer/update/{id}").hasAuthority("ROLE_CUSTOMER") // pass
				 .requestMatchers("/api/customer/upload/profile-pic").hasAuthority("ROLE_CUSTOMER") // pass

			   .requestMatchers("/api/customer/delete/{id}").permitAll()
			   
			   .requestMatchers("/api/address/add").hasAuthority("ROLE_CUSTOMER") // pass
		        .requestMatchers("/api/address/list").hasAuthority("ROLE_CUSTOMER") // pass
		        .requestMatchers("/api/address/delete/{id}").hasAuthority("ROLE_CUSTOMER") 
			   
			   
			   .requestMatchers("/api/cart/add").hasAuthority("ROLE_CUSTOMER") // pass
			   .requestMatchers("/api/cart/items").hasAuthority("ROLE_CUSTOMER") // pass
			   .requestMatchers("/api/cart/remove").hasAuthority("ROLE_CUSTOMER") // pass
			   .requestMatchers("/api/cart/clear").hasAuthority("ROLE_CUSTOMER")
			   
			   
			   .requestMatchers("/api/cart/apply-coupon").hasAuthority("ROLE_CUSTOMER")
			   
			   .requestMatchers("/api/orders/place").hasAuthority("ROLE_CUSTOMER") // pass
			   .requestMatchers("/api/orders/customer/{username}").hasAuthority("ROLE_CUSTOMER")
//			   .requestMatchers("/api/orders/my-orders").hasAnyAuthority("ROLE_CUSTOMER")
			   
			   .requestMatchers("/api/orders/seller/orders").hasAuthority("ROLE_SELLER")
			   
			   // charts
			   
//			   .requestMatchers("/api/orders/seller/saleschart").hasAuthority("ROLE_SELLER") // pass
			   .requestMatchers("/api/orders/seller/chart/top-products").hasAuthority("ROLE_SELLER")
			    
			       
			   .requestMatchers("/api/product/by-seller").hasAuthority("ROLE_SELLER")
				.requestMatchers("/api/categories/add").hasAuthority("ROLE_ADMIN")// pass
				.requestMatchers("/api/product/category/{categoryName}").permitAll()
			   
			        
			        
			 
			        
			     
			        
			        
			        
					/* 
					  
					 
	                .requestMatchers("/api/user/signup").permitAll()
	                .requestMatchers("/api/user/token").authenticated()
	                .requestMatchers("/api/seller/add").permitAll()     //admin can add
	                
	                // seller acess
	                
	                .requestMatchers("/api/product/add").hasAuthority("ROLE_SELLER")
	                
	                .requestMatchers("/api/product/update/{id}").hasAuthority("ROLE_SELLER")
	                .requestMatchers("/api/product/delete/{id}").hasAuthority("ROLE_SELLER")
	                .requestMatchers("/api/seller-product/post/{id}").hasAuthority("ROLE_SELLER")
	                .requestMatchers("/api/seller-product/by-seller/{seller_id}").hasAuthority("ROLE_SELLER") // id

	                // every one can access
	                .requestMatchers("/api/product/all").permitAll() // now i updated and added tha pagination concept
	                .requestMatchers("/api/product/{id}").permitAll() // id
	                
	                */
				.anyRequest().authenticated()  
			)
		 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) 
		 .httpBasic(Customizer.withDefaults()); // this activated http basic technique
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) 
			throws Exception {
		  return auth.getAuthenticationManager();
	 }



}
