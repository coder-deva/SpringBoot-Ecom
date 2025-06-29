package com.springboot.ECommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Category;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.CategoryRepository;
import com.springboot.ECommerce.service.CategoryService;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Admin admin;
    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Admin User");

        category = new Category();
        category.setId(1);
        category.setName("Electronics");
        category.setAdmin(admin);
    }

    @Test
    public void testAddCategory_Success() {
        when(adminRepository.getAdminByUsername("adminuser")).thenReturn(admin);

        Category savedCategory = new Category();
        savedCategory.setId(1);
        savedCategory.setName(category.getName());
        savedCategory.setAdmin(category.getAdmin());

        when(categoryRepository.save(category)).thenReturn(savedCategory);

        Category result = categoryService.addCategory(category, "adminuser");

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        assertEquals(admin, result.getAdmin());
        verify(categoryRepository).save(category);
    }

    @Test
    public void testAddCategory_AdminNotFound() {
        when(adminRepository.getAdminByUsername("unknown")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            categoryService.addCategory(category, "unknown"));

        assertEquals("Admin not found", ex.getMessage());
    }

    @Test
    public void testGetAllCategories_Success() {
        when(adminRepository.getAdminByUsername("adminuser")).thenReturn(admin);
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> result = categoryService.getAllCategories("adminuser");

        assertEquals(1, result.size());
        assertEquals("Electronics", result.get(0).getName());
    }

    @Test
    public void testGetAllCategories_AdminNotFound() {
        when(adminRepository.getAdminByUsername("unknown")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            categoryService.getAllCategories("unknown"));

        assertEquals("Admin not found", ex.getMessage());
    }
}