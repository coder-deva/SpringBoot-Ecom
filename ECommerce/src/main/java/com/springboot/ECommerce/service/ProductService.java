

package com.springboot.ECommerce.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.ECommerce.dto.ProductResponse;
import com.springboot.ECommerce.model.Category;
import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.model.ProductCategory;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.repository.CategoryRepository;
import com.springboot.ECommerce.repository.OrderItemRepository;
import com.springboot.ECommerce.repository.ProductCategoryRepository;
import com.springboot.ECommerce.repository.ProductRepository;
import com.springboot.ECommerce.repository.SellerRepository;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;

   
    public ProductService(ProductRepository productRepository, SellerRepository sellerRepository,
			ProductCategoryRepository productCategoryRepository, CategoryRepository categoryRepository,
			OrderItemRepository orderItemRepository) {
		super();
		this.productRepository = productRepository;
		this.sellerRepository = sellerRepository;
		this.productCategoryRepository = productCategoryRepository;
		this.categoryRepository = categoryRepository;
		this.orderItemRepository = orderItemRepository;
	}



	//catgories by using the category name
    
    public ProductResponse addProductForSeller(Product product, String username, List<String> categoryNames) {
        Seller seller = sellerRepository.getSellerByUsername(username);
        if (seller == null) {
            throw new RuntimeException("Seller not found for username: " + username);
        }

        product.setSeller(seller);
        Product savedProduct = productRepository.save(product);

        for (String categoryName : categoryNames) {
            Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryName));

            ProductCategory pc = new ProductCategory();
            pc.setProduct(savedProduct);
            pc.setCategory(category);
            productCategoryRepository.save(pc);
        }

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setTitle(savedProduct.getTitle());
        response.setDescription(savedProduct.getDescription());
        response.setMrpPrice(savedProduct.getMrpPrice());
        response.setSellingPrice(savedProduct.getSellingPrice());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setColor(savedProduct.getColor());
        response.setSeller(savedProduct.getSeller());
        response.setCategoryNames(categoryNames); // already available

        return response;
    }



    // categories by using the id
    
//    public Product addProductForSeller(Product product, String username, List<Integer> categoryIds) {
//        Seller seller = sellerRepository.getSellerByUsername(username);
//
//        if (seller == null) {
//            throw new RuntimeException("Seller not found for username: " + username);
//        }
//
//        product.setSeller(seller);
//        Product savedProduct = productRepository.save(product);
//
//        for (Integer categoryId : categoryIds) {
//            Category category = categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
//
//            ProductCategory pc = new ProductCategory();
//            pc.setProduct(savedProduct);
//            pc.setCategory(category);
//            productCategoryRepository.save(pc);
//        }
//
//        return savedProduct;
//    }
    
    
    public List<ProductResponse> getProductsBySeller(Principal principal) {
        String username = principal.getName();
        Seller seller = sellerRepository.getSellerByUsername(username);
        List<Product> products = productRepository.findProductsBySellerId(seller.getId());

        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            List<String> categoryNames = productCategoryRepository.findByProduct(product.getId())
                    .stream()
                    .map(pc -> pc.getCategory().getName())
                    .collect(Collectors.toList());

            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setDescription(product.getDescription());
            response.setMrpPrice(product.getMrpPrice());
            response.setSellingPrice(product.getSellingPrice());
            response.setImageUrl(product.getImageUrl());
            response.setColor(product.getColor());
            response.setSeller(product.getSeller());
            response.setCategoryNames(categoryNames);

            responses.add(response);
        }

        return responses;
    }


//    public List<Product> getProductsBySeller(Principal principal) {
//        String username = principal.getName();
//        Seller seller = sellerRepository.getSellerByUsername(username);
//        return productRepository.findProductsBySellerId(seller.getId());
//    }
//    
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productCategoryRepository.findProductsByCategoryName(categoryName);
    }


    public List<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).getContent();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product id is not found"));
    }

    public Product updateProduct(int id, Product product) {
        Product dbProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid Product Id"));

        if (product.getTitle() != null)
            dbProduct.setTitle(product.getTitle());
        

        if (product.getDescription() != null)
            dbProduct.setDescription(product.getDescription());

        if (product.getMrpPrice() != 0)
            dbProduct.setMrpPrice(dbProduct.getMrpPrice());

        if (product.getSellingPrice() != 0)
            dbProduct.setSellingPrice(product.getSellingPrice());

        if (product.getImageUrl() != null)
            dbProduct.setImageUrl(product.getImageUrl());

       

        if (product.getColor() != null)
            dbProduct.setColor(product.getColor());

        return productRepository.save(dbProduct);
    }

    public void deleteProductById(int id) {
    	orderItemRepository.deleteByProductId(id);
    	productCategoryRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }
}






//package com.springboot.ECommerce.service;
//
//
//
//
//import java.security.Principal;
//import java.util.List;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//
//import com.springboot.ECommerce.model.Product;
//import com.springboot.ECommerce.model.Seller;
//import com.springboot.ECommerce.repository.CategoryRepository;
//import com.springboot.ECommerce.repository.ProductCategoryRepository;
//import com.springboot.ECommerce.repository.ProductRepository;
//import com.springboot.ECommerce.repository.SellerRepository;
//
//
//
//@Service
//public class ProductService {
//	
//	private ProductRepository productRepository;
//	private SellerRepository sellerRepository;
//	private final ProductCategoryRepository productCategoryRepository;
//	private final CategoryRepository categoryRepository;
//
//
//
//	
//
//
//	public ProductService(ProductRepository productRepository, SellerRepository sellerRepository,
//			ProductCategoryRepository productCategoryRepository, CategoryRepository categoryRepository) {
//		super();
//		this.productRepository = productRepository;
//		this.sellerRepository = sellerRepository;
//		this.productCategoryRepository = productCategoryRepository;
//		this.categoryRepository = categoryRepository;
//	}
//
//
//	public Product addProductForSeller(Product product, String username) {
//
//	    // Find the Seller by username
//	    Seller seller = sellerRepository.getSellerByUsername(username);
//
//	    if (seller == null) {
//	        throw new RuntimeException("Seller not found for username: " + username);
//	    }
//
//	    // Attach Seller to Product
//	    product.setSeller(seller);
//
//	    // Save Product
//	    return productRepository.save(product);
//	}
//
//	
//	public List<Product> getProductsBySeller(Principal principal) {
//        String username = principal.getName();
//        Seller seller = sellerRepository.getSellerByUsername(username);
//        return productRepository.findProductsBySellerId(seller.getId());
//    }
//
///*
//	public List<Product> getAllProducts() {
//		
//		return productRepository.findAll();
//	}
//*/
//	
//	// pagination 
//	
//	public List<Product> getAllProducts(int page ,int size) {
//			
//		// activate tha pageable interface
//		 Pageable pageable = PageRequest.of(page, size);
//		 // Call findAll inbuilt method as pass this pageable interface ref
//			return productRepository.findAll(pageable).getContent();
//		}
//	
//
//
//	public Product getProductById(int id) {
//		
//		return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product id is not found"));
//	}
//
//
//
//	public Product updateProduct(int id, Product product) {
//		
//		// validate the id and fetch the record from db
//		Product dbProduct=productRepository.findById(id)
//				.orElseThrow(()-> new RuntimeException("Invalid Product Id"));
//		
//		//update the product only for non-null fields
//		if(product.getTitle() !=null)
//			dbProduct.setTitle(product.getTitle());
//		
//		if(product.getDescription()!=null)
//			dbProduct.setDescription(product.getDescription());
//
//		 if (product.getMrpPrice() != 0)
//		        dbProduct.setMrpPrice(dbProduct.getMrpPrice());
//
//		if(product.getSellingPrice()!=0)
//			dbProduct.setSellingPrice(product.getSellingPrice());
//
//		if(product.getDiscountPercent()!=0)
//			dbProduct.setDiscountPercent(product.getDiscountPercent());
//
//		if(product.getQuantity() !=0)
//			dbProduct.setQuantity(product.getQuantity());
//
//		if(product.getColor()!=null)
//			dbProduct.setColor(product.getColor());
//		
//		return productRepository.save(dbProduct);
//	}
//
//
//
//	public void deleteProductById(int id) {
//		productRepository.deleteById(id);;
//		
//	}
//
//}
