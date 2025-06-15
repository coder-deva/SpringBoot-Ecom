package com.springboot.ECommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	@Query("select u from User u where u.username=?1")
	User getByUsername(String username);


}