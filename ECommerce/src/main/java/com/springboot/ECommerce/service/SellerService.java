package com.springboot.ECommerce.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.SellerRepository;



@Service
public class SellerService {

    private SellerRepository sellerRepository;
    private UserService userService;
    
    // this i will added for user for the loggin
    
    public SellerService(SellerRepository sellerRepository, UserService userService) {
		super();
		this.sellerRepository = sellerRepository;
		this.userService = userService;
	}
    
	


    public Seller insertSeller(Seller seller) {

        User user = seller.getUser();

        // Check if user already exists
        User userFromDb = userService.findByUsername(user.getUsername());


        if (userFromDb != null) {
            // User already exists,use existing user
            user = userFromDb;
        } else {
            // User does not exist,create new user
            user.setRole(USER_ROLE.ROLE_SELLER);
            user = userService.signUp(user);
        }

        // Attach user to seller
        seller.setUser(user);

        // Save seller in DB
        return sellerRepository.save(seller);
    }



	


	public List<Seller> getAllSellers() {
		return sellerRepository.findAll();
		
	}

	
	public Seller getSellerById(int id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller ID not found"));
    }


	
	public Seller updateSeller(int id, Seller updatedSeller) {
	   
	    Seller dbSeller = sellerRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Invalid Seller ID Provided"));

	   
	    if (updatedSeller.getName() != null)
	        dbSeller.setName(updatedSeller.getName());
	    if (updatedSeller.getEmail() != null)
	        dbSeller.setEmail(updatedSeller.getEmail());
	    if (updatedSeller.getPhone() != null)
	        dbSeller.setPhone(updatedSeller.getPhone());

	    
	    return sellerRepository.save(dbSeller);
	}
	
	public void deleteSeller(int id) {
        sellerRepository.deleteById(id);
    }



	public Seller getSellerByUsername(String username) {
		
		
		return sellerRepository.getSellerByUsername(username);
	}




    
}
