package com.springboot.ECommerce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.service.CustomerService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
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
    
    
    // get customer profile
    @GetMapping("/profile")
    public Customer getCustomerInfo(Principal principal) {
        return customerService.getCustomerByUsername(principal.getName());
    }
    
    
     // update without profile
//    @PutMapping("/update/{id}")
//    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
//        return customerService.updateCustomer(id, customer);
//    }
    
 // update with the profile
    @PutMapping("/update/{id}")
    public Customer updateCustomer(
            @PathVariable int id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return customerService.updateCustomer(id, name, email, phone, file);
    }
    
    
    @PostMapping("/upload/profile-pic")
    public Customer uploadCustomerProfilePic(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        return customerService.uploadCustomerProfilePic(file, principal.getName());
    }
    

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        
    }
}
