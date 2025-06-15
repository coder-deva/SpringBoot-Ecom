package com.springboot.ECommerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.Seller;



public interface SellerRepository extends JpaRepository<Seller, Integer> {
    
	@Query("select s from Seller s where s.user.username=?1")
	Seller getSellerByUsername(String username);
}


