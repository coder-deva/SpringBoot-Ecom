package com.springboot.ECommerce.controller;

import com.springboot.ECommerce.model.Category;
import com.springboot.ECommerce.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Add a new category
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, Principal principal) {
        return ResponseEntity.ok(categoryService.addCategory(category,principal.getName()));
    }

    // Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(Principal principal) {
        return ResponseEntity.ok(categoryService.getAllCategories(principal.getName()));
    }
}
