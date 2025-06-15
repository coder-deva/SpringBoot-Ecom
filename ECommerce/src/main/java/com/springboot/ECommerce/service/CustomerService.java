package com.springboot.ECommerce.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.CustomerRepository;


@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    public Customer registerCustomer(Customer customer) {
        User user = customer.getUser();

        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            user = userFromDb;
        } else {
            user.setRole(USER_ROLE.ROLE_CUSTOMER);
            user = userService.signUp(user);
        }

        customer.setUser(user);

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer updateCustomer(int id, Customer updatedCustomer) {
        Customer dbCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid Customer ID"));

        if (updatedCustomer.getName() != null)
            dbCustomer.setName(updatedCustomer.getName());

        if (updatedCustomer.getEmail() != null)
            dbCustomer.setEmail(updatedCustomer.getEmail());

        if (updatedCustomer.getPhone() != null)
            dbCustomer.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(dbCustomer);
    }

  

    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
