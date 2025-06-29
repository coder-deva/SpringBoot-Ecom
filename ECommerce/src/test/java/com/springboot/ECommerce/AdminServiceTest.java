package com.springboot.ECommerce;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.ECommerce.domain.USER_ROLE;
import com.springboot.ECommerce.model.Admin;
import com.springboot.ECommerce.model.User;
import com.springboot.ECommerce.repository.AdminRepository;
import com.springboot.ECommerce.service.AdminService;
import com.springboot.ECommerce.service.UserService;

public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserService userService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private AdminService adminService;

    private Admin admin;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setUsername("adminuser");
        user.setRole(USER_ROLE.ROLE_ADMIN);

        admin = new Admin();
        admin.setId(1);
        admin.setName("Admin Name");
        admin.setEmail("admin@example.com");
        admin.setUser(user);
    }

    @Test
    public void testAddAdmin_ExistingUser() {
        when(userService.findByUsername("adminuser")).thenReturn(user);
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminService.addAdmin(admin);

        assertNotNull(result);
        assertEquals("Admin Name", result.getName());
        verify(userService).findByUsername("adminuser");
        verify(adminRepository).save(admin);
    }

    @Test
    public void testAddAdmin_NewUser() {
        when(userService.findByUsername("adminuser")).thenReturn(null);

        User newUser = new User();
        newUser.setId(2);
        newUser.setUsername("adminuser");
        newUser.setRole(USER_ROLE.ROLE_ADMIN);

        when(userService.signUp(newUser)).thenReturn(newUser);
        when(adminRepository.save(admin)).thenReturn(admin);

        admin.setUser(newUser);
        Admin result = adminService.addAdmin(admin);

        assertNotNull(result);
        assertEquals(USER_ROLE.ROLE_ADMIN, result.getUser().getRole());
        verify(userService).signUp(newUser);
        verify(adminRepository).save(admin);
    }

    @Test
    public void testGetAdminByUsername_Success() {
        when(adminRepository.getAdminByUsername("adminuser")).thenReturn(admin);

        Admin result = adminService.getAdminByUsername("adminuser");

        assertNotNull(result);
        assertEquals("Admin Name", result.getName());
        verify(adminRepository).getAdminByUsername("adminuser");
    }

    @Test
    public void testUploadAdminProfilePic_InvalidFileType() {
        when(adminRepository.getAdminByUsername("adminuser")).thenReturn(admin);
        when(file.getOriginalFilename()).thenReturn("profile.txt");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adminService.uploadAdminProfilePic(file, "adminuser"));

        assertTrue(ex.getMessage().contains("Invalid"));
    }

    @Test
    public void testUpdateAdmin_InvalidId() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> adminService.updateAdmin(99, "Name", "email", "phone", null));

        assertEquals("Invalid Admin ID Provided", ex.getMessage());
    }
}
