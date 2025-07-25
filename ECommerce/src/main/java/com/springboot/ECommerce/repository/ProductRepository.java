package com.springboot.ECommerce.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
   
	@Query("select p from Product p where p.seller.id = ?1")
    List<Product> findProductsBySellerId(int sellerId);
}
