package com.springboot.ECommerce;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.ECommerce.dto.CartItemResponse;
import com.springboot.ECommerce.model.Cart;
import com.springboot.ECommerce.model.CartItem;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.repository.CartItemRepository;
import com.springboot.ECommerce.repository.CartRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.repository.ProductRepository;
import com.springboot.ECommerce.service.CartService;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Customer customer;
    private Cart cart;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setName("Test Customer");

        cart = new Cart();
        cart.setId(1);
        cart.setCustomer(customer);

        product = new Product();
        product.setId(1);
        product.setTitle("Test Product");
        product.setSellingPrice(100);
    }

    @Test
    public void testAddToCart_NewItem() {
        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(null);

        CartItemResponse response = cartService.addToCart("user", 1, 2);

        assertEquals(1, response.getProductId());
        assertEquals(2, response.getQuantity());
        assertEquals(200, response.getTotalPrice());
    }

    @Test
    public void testAddToCart_ExistingItem() {
        CartItem existingItem = new CartItem();
        existingItem.setId(1);
        existingItem.setCart(cart);
        existingItem.setProduct(product);
        existingItem.setQuantity(1);

        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(existingItem);

        CartItemResponse response = cartService.addToCart("user", 1, 2);

        assertEquals(3, response.getQuantity());
    }

    @Test
    public void testGetCartItems() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(2);

        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findAllByCartId(cart.getId())).thenReturn(List.of(item));

        List<CartItemResponse> items = cartService.getCartItems("user");

        assertEquals(1, items.size());
        assertEquals(2, items.get(0).getQuantity());
    }

    @Test
    public void testRemoveItemOrReduceQuantity_ReduceQuantity() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(2);

        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(item);

        cartService.removeItemOrReduceQuantity("user", 1);

        assertEquals(1, item.getQuantity());
    }

    @Test
    public void testRemoveItemOrReduceQuantity_DeleteItem() {
        CartItem item = new CartItem();
        item.setId(1);
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(1);

        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())).thenReturn(item);

        cartService.removeItemOrReduceQuantity("user", 1);

        verify(cartItemRepository).deleteById(item.getId());
    }

    @Test
    public void testClearCart() {
        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);
        when(cartRepository.findByCustomerId(customer.getId())).thenReturn(Optional.of(cart));

        cartService.clearCart("user");

        verify(cartItemRepository).deleteByCartId(cart.getId());
    }
}
