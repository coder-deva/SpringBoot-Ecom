package com.springboot.ECommerce.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;

@Service
public class AdminService {
	
	private AdminRepository adminRepository;
	private UserService userService;
	

	


	public AdminService(AdminRepository adminRepository, UserService userService) {
		super();
		this.adminRepository = adminRepository;
		this.userService = userService;
	}





	public Admin addAdmin(Admin admin) {
		
		 User user = admin.getUser();

	        // Check if user already exists
	        User userFromDb = userService.findByUsername(user.getUsername());


	        if (userFromDb != null) {
	            // User already exists,use existing user
	            user = userFromDb;
	        } else {
	            // User does not exist,create new user
	            user.setRole(USER_ROLE.ROLE_ADMIN);
	            user = userService.signUp(user);
	        }

	        // Attach user to admin
	        admin.setUser(user);

	        // Save admin in DB
	        return adminRepository.save(admin);
	}
	
	

	public Admin getAdminByUsername(String username) {
		
		
		return adminRepository.getAdminByUsername(username);
	}
	
	
	public Admin uploadAdminProfilePic(MultipartFile file, String username) throws IOException {
	    Admin admin = adminRepository.getAdminByUsername(username);

	    if (admin == null) {
	        throw new RuntimeException("Admin not found");
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

	    admin.setProfilePic(originalFileName);
	    return adminRepository.save(admin);
	}
	
	public Admin updateAdmin(int id, String name, String email, String phone, MultipartFile file) throws IOException {
	   Admin dbAdmin = adminRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Invalid Admin ID Provided"));

	    if (name != null) dbAdmin.setName(name);
	    if (email != null) dbAdmin.setEmail(email);
	    if (phone != null) dbAdmin.setPhone(phone);

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

	        dbAdmin.setProfilePic(originalFileName);
	    }

	    return adminRepository.save(dbAdmin);
	}


	

}
