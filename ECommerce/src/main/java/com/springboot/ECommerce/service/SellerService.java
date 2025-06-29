package com.springboot.ECommerce.service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.dto.SellerOrderResponse;
import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.OrderItemRepository;
import com.springboot.ECommerce.repository.OrderRepository;
import com.springboot.ECommerce.repository.SellerRepository;



@Service
public class SellerService {

    private SellerRepository sellerRepository;
    private UserService userService;
    
    @Autowired
    private AdminRepository adminRepository;

    
    // this i will added for user for the loggin
    
    public SellerService(SellerRepository sellerRepository, UserService userService) {
		super();
		this.sellerRepository = sellerRepository;
		this.userService = userService;
		
	}
    
	


    public Seller insertSeller(Seller seller,String adminUsername) {

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
        
     // Attach admin
        Admin admin = adminRepository.getAdminByUsername(adminUsername);
        seller.setAdmin(admin);

        // Save seller in DB
        return sellerRepository.save(seller);
    }



	


	public List<Seller> getAllSellers(String adminUsername) {
		 Admin admin = adminRepository.getAdminByUsername(adminUsername);
		    if (admin == null) {
		        throw new RuntimeException("Admin not found");
		    }
		return sellerRepository.findAll();
		
	}

	
	public Seller getSellerById(int id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller ID not found"));
    }


	
	public Seller updateSeller(int id, String name, String email, String phone, MultipartFile file) throws IOException {
	    Seller dbSeller = sellerRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Invalid Seller ID Provided"));

	    if (name != null) dbSeller.setName(name);
	    if (email != null) dbSeller.setEmail(email);
	    if (phone != null) dbSeller.setPhone(phone);

	    if (file != null && !file.isEmpty()) {
	        String originalFileName = file.getOriginalFilename();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

	        List<String> allowedExtensions = List.of("jpg", "jpeg", "png", "gif", "svg");
	        if (!allowedExtensions.contains(extension)) {
	            throw new RuntimeException("Invalid file extension: " + extension);
	        }

	        long kbs = file.getSize() / 1024;
	        if (kbs > 3000) {
	            throw new RuntimeException("Image too large. Max size allowed is 3MB");
	        }

	        String uploadFolder = "D:\\VsCode\\REACT\\Hexaware-FSD\\EcomFrontend\\public\\images";
	        Files.createDirectories(Path.of(uploadFolder));
	        Path path = Paths.get(uploadFolder, originalFileName);
	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	        dbSeller.setProfilePic(originalFileName);
	    }

	    return sellerRepository.save(dbSeller);
	}


//	
//	public void deleteSeller(int id) {
//        sellerRepository.deleteById(id);
//    }



	public Seller getSellerByUsername(String username) {
		
		
		return sellerRepository.getSellerByUsername(username);
	}
	
	
	public Seller uploadSellerProfilePic(MultipartFile file, String username) throws IOException {
	    Seller seller = sellerRepository.getSellerByUsername(username);

	    if (seller == null) {
	        throw new RuntimeException("Seller not found");
	    }

	    String originalFileName = file.getOriginalFilename();
	    String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

	    if (!List.of("jpg", "jpeg", "png", "gif", "svg").contains(extension)) {
	        throw new RuntimeException("Invalid f	ile type");
	    }

	    long kbs = file.getSize() / 1024;
	    if (kbs > 3000) {
	        throw new RuntimeException("File size exceeds limit of 3000KB");
	    }

	    String uploadFolder = "D:\\VsCode\\REACT\\Hexaware-FSD\\EcomFrontend\\public\\images";
	    Files.createDirectories(Path.of(uploadFolder));
	    Path path = Paths.get(uploadFolder, originalFileName);
	    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	    seller.setProfilePic(originalFileName);
	    return sellerRepository.save(seller);
	}






    
}
