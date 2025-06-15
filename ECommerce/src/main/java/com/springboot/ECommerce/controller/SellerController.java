package com.springboot.ECommerce.controller;



import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.service.SellerService;




@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    // add the seller
    
    @PostMapping("/add")
    public Seller insertSeller(@RequestBody Seller seller) {
        return sellerService.insertSeller(seller);
    }
    
    // get all seller
    
    @GetMapping("/all")
    public List<Seller> getAllSellers() {
        return sellerService.getAllSellers();
    }
    
    
//    @GetMapping("/{id}")
//    public Seller getSellerById(@PathVariable int id) {
//        return sellerService.getSellerById(id);
//    }
//    
    // get seller using login credintial
    
    // get product by seller id
    
    @GetMapping("/{id}")
    public Seller getSellerById(Principal principal) {
    	// by using the principal we can get the logged in details of the username
    	String username=principal.getName();
    	return sellerService.getSellerByUsername(username);
    }
    
    
    @PutMapping("/update/{id}")
    public Seller updateSeller(@PathVariable int id, @RequestBody Seller seller) {
        return sellerService.updateSeller(id, seller);
    }
    
    
    @DeleteMapping("/delete/{id}")
    public void deleteSeller(@PathVariable int id) {
        sellerService.deleteSeller(id);
    }

  

    
}
