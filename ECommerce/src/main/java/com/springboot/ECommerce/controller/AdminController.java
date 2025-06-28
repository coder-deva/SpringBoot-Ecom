package com.springboot.ECommerce.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.model.Admin;

import com.springboot.ECommerce.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/add")
	public Admin addAdmin(@RequestBody Admin admin) {
		return adminService.addAdmin(admin);
	}
	
	
	// get admin profile
    @GetMapping("/profile")
    public Admin getAdminInfo(Principal principal) {
        return adminService.getAdminByUsername(principal.getName());
    }
    
    // update with the profile
    @PutMapping("/update/{id}")
    public Admin updateAdmin(
            @PathVariable int id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return adminService.updateAdmin(id, name, email, phone, file);
    }
    
    
    
    @PostMapping("/upload/profile-pic")
    public Admin uploadAdminProfilePic(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        return adminService.uploadAdminProfilePic(file, principal.getName());
    }

}
