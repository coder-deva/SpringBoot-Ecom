package com.springboot.ECommerce.controller;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.model.Orders;
import com.springboot.ECommerce.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // place order from the cart using the username and address id and while place the order i send the coupon code to reduce the pruice from the cart and order is placed successfully
    @PostMapping("/place")
    public ResponseEntity<Orders> placeOrder(
            @RequestParam String username,
            @RequestParam Integer addressId,
            @RequestParam(required = false) CouponType coupon
    ) {
        Orders order = orderService.placeOrder(username, addressId, coupon);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(order);
        		
    }

    /**
     * Get all orders placed by a customer.
     */
    @GetMapping("/customer/{username}")
    public ResponseEntity<List<Orders>> getOrdersByCustomer(@PathVariable String username) {
        List<Orders> orders = orderService.getOrdersByCustomer(username);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(orders);
    }
}
