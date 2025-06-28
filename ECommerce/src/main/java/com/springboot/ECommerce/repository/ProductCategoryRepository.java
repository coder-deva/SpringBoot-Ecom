package com.springboot.ECommerce.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.model.ProductCategory;

import jakarta.transaction.Transactional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    @Query("SELECT pc FROM ProductCategory pc WHERE pc.product.id = ?1")
    List<ProductCategory> findByProductId(int productId);
    
    //get products by category name
   
    @Query("SELECT pc.product FROM ProductCategory pc WHERE pc.category.name = :categoryName")
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);
    
    @Query("SELECT pc.category.name FROM ProductCategory pc WHERE pc.product.id = :productId")
    List<String> findCategoryNamesByProductId(@Param("productId") int productId);

   
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductCategory pc WHERE pc.product.id = :productId")
    void deleteByProductId(@Param("productId") int productId);

   
    @Query("SELECT pc FROM ProductCategory pc WHERE pc.product.id = :productId")
    List<ProductCategory> findByProduct(@Param("productId") int productId);

}
