package com.springboot.ECommerce.service;

import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Category;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private AdminRepository adminRepository;
    
    
   

    public CategoryService(CategoryRepository categoryRepository, AdminRepository adminRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.adminRepository = adminRepository;
	}


	public Category addCategory(Category category, String username) {
        Admin admin = adminRepository.getAdminByUsername(username);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }
        category.setAdmin(admin);
        return categoryRepository.save(category);
    }


    public List<Category> getAllCategories(String adminUsername) {
    	Admin admin = adminRepository.getAdminByUsername(adminUsername);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }
        return categoryRepository.findAll();
    }
}