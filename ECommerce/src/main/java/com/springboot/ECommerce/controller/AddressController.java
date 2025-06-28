package com.springboot.ECommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.springboot.ECommerce.model.Address;
import com.springboot.ECommerce.service.AddressService;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody Address address, Principal principal) {
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(addressService.addAddress(principal, address));
        		
    }

    @GetMapping("/list") // List<Address>
    public ResponseEntity<?> getMyAddresses(Principal principal) {
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body(addressService.getMyAddresses(principal));
        		
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable int id, Principal principal) {
        addressService.deleteAddress(principal, id);
        return ResponseEntity
        		.status(HttpStatus.OK)
        		.body("Address deleted successfully");
        		
        		
    }

}