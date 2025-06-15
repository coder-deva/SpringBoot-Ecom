package com.springboot.ECommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.domain.OrderStatus;
import com.springboot.ECommerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // Get all order items by Order ID
    @Query("select oi from OrderItem oi where oi.order.id = ?1")
    List<OrderItem> findByOrderId(int orderId);

    // Get all order items by Customer ID (via order)
    @Query("select oi from OrderItem oi where oi.order.customer.id = ?1")
    List<OrderItem> findByCustomerId(int customerId);
    
    // Get all items by status
    
    @Query("select oi from OrderItem oi where oi.status = ?1")
    List<OrderItem> findByStatus(OrderStatus status);
}
