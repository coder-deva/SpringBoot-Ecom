package com.springboot.ECommerce.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ECommerce.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("select a from Address a where a.customer.id = ?1")
    List<Address> findByCustomerId(int customerId,Pageable pageable);
}
