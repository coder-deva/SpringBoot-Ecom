package com.springboot.ECommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ECommerce.domain.OrderStatus;
import com.springboot.ECommerce.dto.SellerOrderResponse;

import com.springboot.ECommerce.model.OrderItem;
import com.springboot.ECommerce.model.Orders;

import jakarta.transaction.Transactional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    // Get all order items by Order id
    @Query("select oi from OrderItem oi where oi.order.id = ?1")
    List<OrderItem> findByOrderId(int orderId);

    // Get all order items by Customer id
    @Query("select oi from OrderItem oi where oi.order.customer.id = ?1")
    List<OrderItem> findByCustomerId(int customerId);
    
    // Get all items by status
    
    @Query("select oi from OrderItem oi where oi.status = ?1")
    List<OrderItem> findByStatus(OrderStatus status);
    
    List<OrderItem> findByOrder(Orders order);

    
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.seller.user.username = ?1 ORDER BY oi.order.orderDate DESC")
    List<OrderItem> findOrdersBySellerUsername(String username,Pageable pageable);


    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.seller.user.username = ?1")
    List<OrderItem> findOrderItemsBySellerUsername(String username);
    
    
    
    
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderItem oi WHERE oi.product.id = :productId")
    void deleteByProductId(@Param("productId") int productId);
    
    
    
    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.order.id = :orderId")
    void deleteByOrderId(@Param("orderId") int orderId);



    
    


}
