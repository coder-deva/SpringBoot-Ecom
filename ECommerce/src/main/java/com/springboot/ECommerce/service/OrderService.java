package com.springboot.ECommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.domain.OrderStatus;

import com.springboot.ECommerce.model.*;
import com.springboot.ECommerce.repository.*;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(CustomerRepository customerRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        ProductRepository productRepository,
                        AddressRepository addressRepository,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Orders placeOrder(String username, Integer addressId, CouponType coupon) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        Cart cart = cartRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");

        Address selectedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        double originalTotal = 0.0;
        for (CartItem item : cartItems) {
            originalTotal += item.getProduct().getSellingPrice() * item.getQuantity();
        }

        double finalTotal = applyCouponDiscount(originalTotal, coupon);
        double discountAmount = originalTotal - finalTotal;

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAddress(selectedAddress);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setCoupon(coupon);
        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(finalTotal);

        Orders savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getSellingPrice());
            orderItem.setStatus(OrderStatus.PENDING);
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCartId(cart.getId());

        return savedOrder;
    }



    private double applyCouponDiscount(double amount, CouponType coupon) {
        if (coupon == null) return amount;
        double discountedAmount;
        switch (coupon) {
            case FESTIVAL_DISCOUNT10 -> discountedAmount = amount * 0.90;
            case WELCOME_OFFER20 -> discountedAmount = amount * 0.80;
            case SEASONAL_SALE15 -> discountedAmount = amount * 0.85;
            case CLEARANCE_SALE10 -> discountedAmount = amount * 0.90;
            default -> discountedAmount = amount;
        }
        return Math.round(discountedAmount * 100.0) / 100.0;
    }
    
    public List<Orders> getOrdersByCustomer(String username) {
        Customer customer = customerRepository.getCustomerByUsername(username);
        return orderRepository.findByCustomerId(customer.getId());
    }


}
