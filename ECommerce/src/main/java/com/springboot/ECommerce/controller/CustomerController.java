package com.springboot.ECommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.service.CustomerService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.registerCustomer(customer);
        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body(savedCustomer);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(customerService.getAllCustomers());
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }
  

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity
        		.noContent()
        		.build();
    }
}
