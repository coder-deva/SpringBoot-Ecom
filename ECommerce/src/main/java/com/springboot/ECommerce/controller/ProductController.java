package com.springboot.ECommerce.controller;



import java.security.Principal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ECommerce.dto.ProductRequest;
import com.springboot.ECommerce.dto.ProductResponse;
import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.service.ProductService;


@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
	
	 @Autowired
	 private ProductService productService;
	 
	 Logger logger = LoggerFactory.getLogger("ProductController");
	 
	 // add product should be done by seller with category
	 
	 @PostMapping("/add")
	 public ResponseEntity<?> addProduct(@RequestBody ProductRequest request, Principal principal) {
	     ProductResponse saved = productService.addProductForSeller(
	         request.getProduct(),
	         principal.getName(),
	         request.getCategoryNames()
	     );
	     return ResponseEntity.ok(saved);
	 }


	 
	 
	 
	 
	 
//	 @PostMapping("/add")
//	 public ResponseEntity<Product> addProduct(@RequestBody Product product, Principal principal) {
//	     String username = principal.getName();
//
//	     Product savedProduct = productService.addProductForSeller(product, username);
//
//	     return ResponseEntity
//	    		 .status(HttpStatus.OK)
//	    		 .body(savedProduct);
//	    		 
//	 }

	 
	 
	 
	 @GetMapping("/all")
	 public List<Product> getAllProducts() {
	     return productService.getAllProducts();
	    }
	    
	 
	 // pagination
//	 
//	 @GetMapping("/all")
//	 public List<Product> getAllProducts(
//			 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
//			 @RequestParam(name = "size", required = false, defaultValue = "1000000") Integer size
//			 							) {
//		 if (page == 0 && size == 1000000)
//				logger.info("No Pagination call for all products");
//		 
//	     return productService.getAllProducts(page,size);
//	    }
	 
	 @GetMapping("/by-seller")
	 public ResponseEntity<List<ProductResponse>> getProductsBySeller(Principal principal) {
	     List<ProductResponse> products = productService.getProductsBySeller(principal);
	     return ResponseEntity.status(HttpStatus.OK).body(products);
	 }

	 
	 
//	 @GetMapping("/by-seller")
//	    public ResponseEntity<List<Product>> getProductsBySeller(Principal principal) {
//	        List<Product> products = productService.getProductsBySeller(principal);
//	        return ResponseEntity
//	        		.status(HttpStatus.OK)
//	        		.body(products);
//	        		
//	    }
	 
	 @GetMapping("/category/{categoryName}")
	 public ResponseEntity<?> getProductsByCategoryName(@PathVariable String categoryName) {
	     List<Product> products = productService.getProductsByCategoryName(categoryName);
	     return ResponseEntity.ok(products);
	 }

	 
	 
	 @GetMapping("/{id}")
	    public Product getProductById(@PathVariable int id) {
	        return productService.getProductById(id);
	    }
	 
	 @PutMapping("/update/{id}")
	    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
	        return productService.updateProduct(id, product);
	    }
	 
	 
	 @DeleteMapping("/delete/{id}")
	    public void deleteProductById(@PathVariable int id) {
	        productService.deleteProductById(id);
	    }
	 
	 
	 
	 

}

