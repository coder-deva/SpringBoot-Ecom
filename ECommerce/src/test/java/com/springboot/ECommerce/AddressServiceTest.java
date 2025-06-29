package com.springboot.ECommerce;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import com.springboot.ECommerce.model.Address;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.repository.AddressRepository;
import com.springboot.ECommerce.repository.CustomerRepository;
import com.springboot.ECommerce.service.AddressService;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private AddressService addressService;

    private Customer customer;
    private Address address;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");

        address = new Address();
        address.setAddressId(10);
        address.setStreet("123 Main St");
        address.setCustomer(customer);
    }

    @Test
    public void testAddAddress_Success() {
        when(principal.getName()).thenReturn("user1");
        when(customerRepository.getCustomerByUsername("user1")).thenReturn(customer);
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.addAddress(principal, address);

        assertNotNull(result);
        assertEquals("123 Main St", result.getStreet());
        verify(addressRepository).save(address);
    }

    @Test
    public void testGetMyAddresses_Success() {
        when(principal.getName()).thenReturn("user1");
        when(customerRepository.getCustomerByUsername("user1")).thenReturn(customer);

        PageRequest pageable = PageRequest.of(0, 5);
        when(addressRepository.findByCustomerId(1, pageable)).thenReturn(List.of(address));

        List<Address> result = addressService.getMyAddresses(principal, 0, 5);

        assertEquals(1, result.size());
        assertEquals("123 Main St", result.get(0).getStreet());
    }

    @Test
    public void testDeleteAddress_Success() {
        when(principal.getName()).thenReturn("user1");
        when(customerRepository.getCustomerByUsername("user1")).thenReturn(customer);
        when(addressRepository.findById(10)).thenReturn(Optional.of(address));

        assertDoesNotThrow(() -> addressService.deleteAddress(principal, 10));
        verify(addressRepository).deleteById(10);
    }

    @Test
    public void testDeleteAddress_Unauthorized() {
        Customer otherCustomer = new Customer();
        otherCustomer.setId(2);
        address.setCustomer(otherCustomer);

        when(principal.getName()).thenReturn("user1");
        when(customerRepository.getCustomerByUsername("user1")).thenReturn(customer);
        when(addressRepository.findById(10)).thenReturn(Optional.of(address));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.deleteAddress(principal, 10));
        assertEquals("Unauthorized to delete this address", ex.getMessage());
    }

    @Test
    public void testDeleteAddress_NotFound() {
        when(principal.getName()).thenReturn("user1");
        when(customerRepository.getCustomerByUsername("user1")).thenReturn(customer);
        when(addressRepository.findById(10)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> addressService.deleteAddress(principal, 10));
        assertEquals("Address not found", ex.getMessage());
    }
}
