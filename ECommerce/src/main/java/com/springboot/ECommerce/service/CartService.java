package com.springboot.ECommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.ECommerce.dto.CartItemResponse;
import com.springboot.ECommerce.exception.ResourceNotFoundException;
import com.springboot.ECommerce.model.Cart;
import com.springboot.ECommerce.model.CartItem;
import com.springboot.ECommerce.model.Coupon;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.repository.CartItemRepository;
import com.springboot.ECommerce.repository.CartRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.repository.ProductRepository;

@Service
public class CartService {
	
	@Autowired
	CouponService couponService;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    private Cart getCartByCustomerUsername(String username) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        return cartRepository.findByCustomerId(customer.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });
    }

    public CartItemResponse addToCart(String username, int productId, int quantity) {
        Cart cart = getCartByCustomerUsername(username);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
            return CartItemResponse.convertToDto(existing);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
            return CartItemResponse.convertToDto(newItem);
        }
    }

    public List<CartItemResponse> getCartItems(String username) {
        Cart cart = getCartByCustomerUsername(username);
        List<CartItem> items = cartItemRepository.findAllByCartId(cart.getId());
        return items.stream()
                .map(CartItemResponse::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void clearCart(String username) {
        Cart cart = getCartByCustomerUsername(username);
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Transactional
    public void removeItemOrReduceQuantity(String username, int productId) {
        Cart cart = getCartByCustomerUsername(username);
        CartItem existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (existing != null) {
            if (existing.getQuantity() > 1) {
                existing.setQuantity(existing.getQuantity() - 1);
                cartItemRepository.save(existing);
            } else {
                cartItemRepository.deleteById(existing.getId());
            }
        } else {
            throw new RuntimeException("Item not found in cart");
        }
    }
    
    
}
