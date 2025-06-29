package com.springboot.ECommerce;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.model.Category;
import com.springboot.ECommerce.model.ProductCategory;
import com.springboot.ECommerce.repository.ProductRepository;
import com.springboot.ECommerce.repository.SellerRepository;
import com.springboot.ECommerce.repository.ProductCategoryRepository;
import com.springboot.ECommerce.repository.CategoryRepository;
import com.springboot.ECommerce.repository.OrderItemRepository;
import com.springboot.ECommerce.service.ProductService;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductForSeller() {
        String username = "seller1";
        Seller seller = new Seller();
        seller.setId(1);

        Product product = new Product();
        product.setId(1);
        product.setTitle("New Product");

        Category category = new Category();
        category.setName("Electronics");

        when(sellerRepository.getSellerByUsername(username)).thenReturn(seller);
        when(productRepository.save(product)).thenReturn(product);
        when(categoryRepository.findByNameIgnoreCase("Electronics")).thenReturn(Optional.of(category));

        var response = productService.addProductForSeller(product, username, List.of("Electronics"));

        assertNotNull(response);
        assertEquals("New Product", response.getTitle());
        assertEquals(List.of("Electronics"), response.getCategoryNames());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1);
        product.setTitle("Test Product");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);
        assertEquals("Test Product", result.getTitle());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(2));

        assertTrue(exception.getMessage().contains("Product id is not found"));
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setId(1);

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = productService.getAllProducts();

        assertEquals(1, result.size());
    }

    @Test
    void testDeleteProductById() {
        doNothing().when(orderItemRepository).deleteByProductId(1);
        doNothing().when(productCategoryRepository).deleteByProductId(1);
        doNothing().when(productRepository).deleteById(1);

        assertDoesNotThrow(() -> productService.deleteProductById(1));
    }
}
