package com.springboot.ECommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ECommerce.model.Cart;
import com.springboot.ECommerce.model.CartItem;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	/*
	@Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1") //Gets all items for a specific cart.
    List<CartItem> findAllByCartId(int cartId);

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = ?1")
	CartItem findByCartIdAndProductId(int cartId, int productId); // Helps with checking/updating specific cart item quantity.

    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = ?1")  // it clear all cartitems
    void deleteByCartId(int cartId);
    */
	
	
	
	 	@Query("select ci from CartItem ci where ci.cart.id = ?1")
	    List<CartItem> findAllByCartId(@Param("cartId") int cartId);

//	    @Query("select ci from CartItem ci where ci.cart.id = :cartId and ci.product.id = ?1")
//	    CartItem findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);
	 	
	 	@Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
	 	CartItem findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") int productId);


	    @Transactional
	    @Modifying
	    @Query("delete from CartItem ci where ci.cart.id = ?1")
	    void deleteByCartId(@Param("cartId") int cartId);

}
