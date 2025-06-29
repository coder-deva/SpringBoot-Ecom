package com.springboot.ECommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ECommerce.model.Orders;
import com.springboot.ECommerce.model.Customer;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
	

    @Query("select o from Orders o where o.customer.id =?1")
    List<Orders> findOrdersByCustomerId(int customerId);
    

    @Query("select o from Orders o where o.customer = ?1")
    List<Orders> findOrdersByCustomer(Customer customer);
    
    


    @Query("select o from Orders o where o.customer.id = ?1 ORDER BY o.orderDate DESC")
    List<Orders> findByCustomerId(int customerId,Pageable pageable);


}
