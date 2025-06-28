package com.springboot.ECommerce.controller;



import java.io.IOException;
import java.security.Principal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.service.SellerService;




@RestController
@RequestMapping("/api/seller")
@CrossOrigin(origins = "http://localhost:5173")
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
    
    
    // get seller profile
    @GetMapping("/profile")
    public Seller getSellerInfo(Principal principal) {
        return sellerService.getSellerByUsername(principal.getName());
    }
    
    // update with the profile
    @PutMapping("/update/{id}")
    public Seller updateSeller(
            @PathVariable int id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return sellerService.updateSeller(id, name, email, phone, file);
    }
    
    
    
    @PostMapping("/upload/profile-pic")
    public Seller uploadSellerProfilePic(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        return sellerService.uploadSellerProfilePic(file, principal.getName());
    }

    
    
    @DeleteMapping("/delete/{id}")
    public void deleteSeller(@PathVariable int id) {
        sellerService.deleteSeller(id);
    }
    
   


  

    
}
