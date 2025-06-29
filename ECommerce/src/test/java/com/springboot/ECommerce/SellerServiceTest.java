package com.springboot.ECommerce;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.repository.SellerRepository;
import com.springboot.ECommerce.service.SellerService;
import com.springboot.ECommerce.service.UserService;

public class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private UserService userService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private SellerService sellerService;

    private Admin admin;
    private Seller seller;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setUsername("selleruser");
        user.setRole(USER_ROLE.ROLE_SELLER);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Admin User");

        seller = new Seller();
        seller.setId(1);
        seller.setName("Seller Name");
        seller.setEmail("seller@example.com");
        seller.setUser(user);
        seller.setAdmin(admin);
    }

    
   

    

    @Test
    public void testGetSellerById_Success() {
        when(sellerRepository.findById(1)).thenReturn(Optional.of(seller));

        Seller result = sellerService.getSellerById(1);

        assertNotNull(result);
        assertEquals("Seller Name", result.getName());
    }

    @Test
    public void testGetSellerById_NotFound() {
        when(sellerRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            sellerService.getSellerById(99));

        assertEquals("Seller ID not found", ex.getMessage());
    }

   

    @Test
    public void testUpdateSeller_InvalidId() {
        when(sellerRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            sellerService.updateSeller(99, "Name", "email", "phone", null));

        assertEquals("Invalid Seller ID Provided", ex.getMessage());
    }

    

    @Test
    public void testUploadSellerProfilePic_SellerNotFound() {
        when(sellerRepository.getSellerByUsername("unknown")).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
            sellerService.uploadSellerProfilePic(file, "unknown"));

        assertEquals("Seller not found", ex.getMessage());
    }
}
