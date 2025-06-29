package com.springboot.ECommerce;



import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.repository.SellerRepository;
import com.springboot.ECommerce.repository.UserRepository;
import com.springboot.ECommerce.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpass");
        user.setRole(USER_ROLE.ROLE_CUSTOMER);

        when(passwordEncoder.encode("plainpass")).thenReturn("hashedpass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.signUp(user);

        assertEquals("hashedpass", savedUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.getByUsername("testuser")).thenReturn(user);

        User found = userService.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }

    @Test
    public void testGetUserInfo_Customer() {
        User user = new User();
        user.setUsername("cust");
        user.setRole(USER_ROLE.ROLE_CUSTOMER);

        Customer customer = new Customer();
        customer.setName("Cust Name");

        when(userRepository.findByUsername("cust")).thenReturn(user);
        when(customerRepository.getCustomerByUsername("cust")).thenReturn(customer);

        Object result = userService.getUserInfo("cust");
        assertTrue(result instanceof Customer);
        assertEquals("Cust Name", ((Customer) result).getName());
    }

    @Test
    public void testGetUserInfo_Seller() {
        User user = new User();
        user.setUsername("seller");
        user.setRole(USER_ROLE.ROLE_SELLER);

        Seller seller = new Seller();
        seller.setName("Seller Name");

        when(userRepository.findByUsername("seller")).thenReturn(user);
        when(sellerRepository.getSellerByUsername("seller")).thenReturn(seller);

        Object result = userService.getUserInfo("seller");
        assertTrue(result instanceof Seller);
        assertEquals("Seller Name", ((Seller) result).getName());
    }

    @Test
    public void testGetUserInfo_Admin() {
        User user = new User();
        user.setUsername("admin");
        user.setRole(USER_ROLE.ROLE_ADMIN);

        Admin admin = new Admin();
        admin.setName("Admin Name");

        when(userRepository.findByUsername("admin")).thenReturn(user);
        when(adminRepository.getAdminByUsername("admin")).thenReturn(admin);

        Object result = userService.getUserInfo("admin");
        assertTrue(result instanceof Admin);
        assertEquals("Admin Name", ((Admin) result).getName());
    }

}
