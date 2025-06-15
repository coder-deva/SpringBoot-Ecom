package com.springboot.ECommerce.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ECommerce.model.Address;
import com.springboot.ECommerce.model.Customer;
import com.springboot.ECommerce.repository.AddressRepository;
import com.springboot.ECommerce.repository.CustomerRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public Address addAddress(Principal principal, Address address) {
        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        address.setCustomer(customer);
        return addressRepository.save(address);
    }

    public List<Address> getMyAddresses(Principal principal) {
        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        return addressRepository.findByCustomerId(customer.getId());
    }

    public void deleteAddress(Principal principal, int addressId) {
        Customer customer = customerRepository.getCustomerByUsername(principal.getName());
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (address.getCustomer().getId() != customer.getId()) {
            throw new RuntimeException("Unauthorized to delete this address");
        }

        addressRepository.deleteById(addressId);
    }
}
