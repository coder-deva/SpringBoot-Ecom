package com.springboot.ECommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.ECommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	 Optional<Category> findByNameIgnoreCase(String name);
}
