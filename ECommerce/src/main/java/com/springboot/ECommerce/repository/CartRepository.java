package com.springboot.ECommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.Cart;
import com.springboot.ECommerce.model.Customer;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	 @Query("select c from Cart c where c.customer.id = ?1")
	    Optional<Cart> findByCustomerId(int customerId);
}