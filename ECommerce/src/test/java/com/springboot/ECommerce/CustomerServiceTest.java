package com.springboot.ECommerce;




import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.service.CustomerService;
import com.springboot.ECommerce.service.UserService;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserService userService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCustomer_NewUser() {
        Customer customer = new Customer();
        User user = new User();
        user.setUsername("newuser");
        customer.setUser(user);

        when(userService.findByUsername("newuser")).thenReturn(null);
        when(userService.signUp(any(User.class))).thenReturn(user);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.registerCustomer(customer);
        assertNotNull(result);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    public void testGetAllCustomers() {
        customerService.getAllCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    public void testGetCustomerByUsername() {
        Customer customer = new Customer();
        when(customerRepository.getCustomerByUsername("user")).thenReturn(customer);

        Customer result = customerService.getCustomerByUsername("user");
        assertNotNull(result);
    }

    @Test
    public void testDeleteCustomer() {
        customerService.deleteCustomer(1);
        verify(customerRepository).deleteById(1);
    }

    @Test
    public void testUpdateCustomer_WithInvalidId_Throws() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(1, "name", "email", "phone", file));
    }
}

