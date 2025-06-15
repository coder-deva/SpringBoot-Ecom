package com.springboot.ECommerce.model;




import com.springboot.ECommerce.domain.USER_ROLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_info")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String username;

	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private USER_ROLE role;
	
	
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}

	
	
	
	
	
}
