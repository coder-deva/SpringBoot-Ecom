package com.springboot.ECommerce.controller;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.domain.OrderStatus;
import com.springboot.ECommerce.dto.CustomerOrderResponse;
import com.springboot.ECommerce.dto.SellerOrderResponse;

import com.springboot.ECommerce.dto.WeeklyTopProductResponse;
import com.springboot.ECommerce.model.Orders;
import com.springboot.ECommerce.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

	@Autowired
    private OrderService orderService;

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
//    @GetMapping("/customer/{username}")
//    public ResponseEntity<List<Orders>> getOrdersByCustomer(@PathVariable String username,
//    		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
//		    @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
//        List<Orders> orders = orderService.getOrdersByCustomer(username,page,size);
//        return ResponseEntity
//        		.status(HttpStatus.OK)
//        		.body(orders);
//    }
//    
    
    @GetMapping("/customer/{username}")
    public ResponseEntity<List<CustomerOrderResponse>> getOrdersByCustomer(
            @PathVariable String username,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
        
        List<CustomerOrderResponse> orders = orderService.getOrdersByCustomer(username, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    
    @GetMapping("/seller/orders")
    public ResponseEntity<List<SellerOrderResponse>> getOrdersForSeller(Principal principal,
    		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
		    @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size) {
        String username = principal.getName();
        List<SellerOrderResponse> orders = orderService.getOrdersForSeller(username,page,size);
        return ResponseEntity.ok(orders);
    }
    
   
    
//    @GetMapping("/seller/saleschart")
//    public ResponseEntity<List<SellerSalesChartResponse>> getSellerSalesChart(Principal principal) {
//        List<SellerSalesChartResponse> chartData = orderService.getSellerSalesChart(principal.getName());
//        return ResponseEntity.ok(chartData);
//    }
    
    @GetMapping("/seller/chart/top-products")
    public ResponseEntity<List<WeeklyTopProductResponse>> getTopProductsThisWeek(Principal principal) {
        List<WeeklyTopProductResponse> response = orderService.getTopSellingProductsThisWeek(principal.getName());
        return ResponseEntity.ok(response);
    }

    
    // update the order status
    @PutMapping("/seller/order-item/status/{id}")
    public ResponseEntity<String> updateOrderItemStatus(
        @PathVariable("id") int orderItemId,
        @RequestParam("status") String newStatusStr) {

        try {
            OrderStatus newStatus = OrderStatus.valueOf(newStatusStr.toUpperCase());
            orderService.updateOrderItemStatus(orderItemId, newStatus);
            return ResponseEntity.ok("Order item status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    
    //delete order
    
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }






}
