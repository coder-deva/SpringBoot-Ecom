package com.springboot.ECommerce.controller;

import com.springboot.ECommerce.dto.CartItemResponse;
import com.springboot.ECommerce.service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
    private final CartService cartService;

   
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to cart
    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addToCart(
            @RequestParam int productId,
            @RequestParam(defaultValue = "1") int quantity,
            Principal principal) {

        String username = principal.getName();
        CartItemResponse response = cartService.addToCart(username, productId, quantity);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(response);
        		
    }

    //Get all items in the cart
    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(Principal principal) {
        String username = principal.getName();
        List<CartItemResponse> items = cartService.getCartItems(username);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(items);
    }

    //Remove or reduce quantity of a cart item until it becomes < 1 or 0 
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeItem(
            @RequestParam int productId,
            Principal principal) {

        String username = principal.getName();
        cartService.removeItemOrReduceQuantity(username, productId);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body("Item reduced or removed successfully");
    }

    //Clear all items from the cart
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal) {
        String username = principal.getName();
        cartService.clearCart(username);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body("Cart cleared successfully");
    }
    
    

}
