package com.springboot.ECommerce.service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.domain.OrderStatus;
import com.springboot.ECommerce.dto.CustomerOrderResponse;
import com.springboot.ECommerce.dto.OrderItemResponse;
import com.springboot.ECommerce.dto.SellerOrderResponse;

import com.springboot.ECommerce.dto.WeeklyTopProductResponse;
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


   

    public OrderService(CustomerRepository customerRepository, CartRepository cartRepository,
			CartItemRepository cartItemRepository, ProductRepository productRepository,
			AddressRepository addressRepository, OrderRepository orderRepository,
			OrderItemRepository orderItemRepository) {
		super();
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
        order.setStatus(OrderStatus.PLACED);
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
            orderItem.setStatus(OrderStatus.PLACED);
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

//    public List<Orders> getOrdersByCustomer(String username,int page,int size) {
//        Customer customer = customerRepository.getCustomerByUsername(username);
//        Pageable pageable = PageRequest.of(page, size);
//        return orderRepository.findByCustomerId(customer.getId(),pageable);
//    }
    
    public List<CustomerOrderResponse> getOrdersByCustomer(String username, int page, int size) {
    	Customer customer = customerRepository.getCustomerByUsername(username);
    	if (customer == null) {
    	    throw new RuntimeException("Customer not found");
    	}

        Pageable pageable = PageRequest.of(page, size);
        List<Orders> orders = orderRepository.findByCustomerId(customer.getId(), pageable);

        List<CustomerOrderResponse> responseList = new ArrayList<>();

        for (Orders order : orders) {
            CustomerOrderResponse response = new CustomerOrderResponse();
            response.setOrderId(order.getId());
            response.setOrderDate(order.getOrderDate());
            response.setTotalAmount(order.getTotalAmount());
            response.setStatus(order.getStatus().toString());

            Address addr = order.getAddress();
            if (addr != null) {
                response.setFirstName(addr.getFirstName());
                response.setLastName(addr.getLastName());
                response.setStreet(addr.getStreet());
                response.setCity(addr.getCity());
                response.setState(addr.getState());
                response.setZipCode(addr.getZipCode());
            }

            List<OrderItemResponse> itemResponses = orderItemRepository.findByOrderId(order.getId())
                .stream()
                .map(item -> {
                    OrderItemResponse itemDto = new OrderItemResponse();
                    itemDto.setProductTitle(item.getProduct().getTitle());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setPrice(item.getPrice());
                    itemDto.setStatus(item.getStatus().toString());
                    itemDto.setProductImageUrl(item.getProduct().getImageUrl());
                    return itemDto;
                }).toList();

            response.setItems(itemResponses);
            responseList.add(response);
        }


        return responseList;
    }

    
    public List<SellerOrderResponse> getOrdersForSeller(String username,int page,int size) {
    	 Pageable pageable = PageRequest.of(page, size);
        List<OrderItem> orderItems = orderItemRepository.findOrdersBySellerUsername(username,pageable);
       
        List<SellerOrderResponse> responses = new ArrayList<>();

        for (OrderItem oi : orderItems) {
            SellerOrderResponse response = new SellerOrderResponse();
            response.setCustomerName(oi.getOrder().getCustomer().getName());
            response.setCustomerEmail(oi.getOrder().getCustomer().getEmail());
            response.setProductTitle(oi.getProduct().getTitle());
            response.setQuantity(oi.getQuantity());
            response.setPrice(oi.getPrice());
            response.setStatus(oi.getStatus().toString());
            response.setOrderItemId(oi.getId()); 
            response.setCustomerStreet(oi.getOrder().getAddress().getStreet());
            response.setCustomerCity(oi.getOrder().getAddress().getCity());
            response.setCustomerState(oi.getOrder().getAddress().getState());
            response.setCustomerZipCode(oi.getOrder().getAddress().getZipCode());
            response.setOrderDate(oi.getOrder().getOrderDate());

            responses.add(response);
        }

        return responses;
    }

    
   

	public List<WeeklyTopProductResponse> getTopSellingProductsThisWeek(String username) {
	    List<OrderItem> items = orderItemRepository.findOrderItemsBySellerUsername(username);
	
	    Map<String, Long> productCountMap = new HashMap<>();
	
	    for (OrderItem item : items) {
	        String title = item.getProduct().getTitle();
	        long qty = item.getQuantity();
	        productCountMap.put(title, productCountMap.getOrDefault(title, 0L) + qty);
	    }
	
	    return productCountMap.entrySet().stream()
	        .map(entry -> new WeeklyTopProductResponse(entry.getKey(), entry.getValue()))
	        .sorted((a, b) -> Long.compare(b.getTotalQuantitySold(), a.getTotalQuantitySold())) // highest first
	        .collect(Collectors.toList());
	}
	

	
	// update the order status

    public void updateOrderItemStatus(int orderItemId, OrderStatus newStatus) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        item.setStatus(newStatus);
        orderItemRepository.save(item);

        Orders parentOrder = item.getOrder();
        List<OrderItem> allItems = orderItemRepository.findByOrderId(parentOrder.getId());

        boolean allMatch = allItems.stream().allMatch(i -> i.getStatus() == newStatus);
        if (allMatch) {
            parentOrder.setStatus(newStatus);
            orderRepository.save(parentOrder);
        }
    }

	
	@Transactional
	public void deleteOrder(int orderId) {
	    Orders order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));
	    orderItemRepository.deleteByOrderId(orderId);
	    orderRepository.delete(order);
	}







}
