package com.springboot.ECommerce.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.user.username = ?1")
    Customer getCustomerByUsername(String username);
}
