package com.springboot.ECommerce.service;




import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.ECommerce.model.Product;
import com.springboot.ECommerce.model.Seller;
import com.springboot.ECommerce.repository.ProductRepository;
import com.springboot.ECommerce.repository.SellerRepository;



@Service
public class ProductService {
	
	private ProductRepository productRepository;
	private SellerRepository sellerRepository;



	public ProductService(ProductRepository productRepository, SellerRepository sellerRepository) {
		super();
		this.productRepository = productRepository;
		this.sellerRepository = sellerRepository;
	}


	public Product addProductForSeller(Product product, String username) {

	    // Find the Seller by username
	    Seller seller = sellerRepository.getSellerByUsername(username);

	    if (seller == null) {
	        throw new RuntimeException("Seller not found for username: " + username);
	    }

	    // Attach Seller to Product
	    product.setSeller(seller);

	    // Save Product
	    return productRepository.save(product);
	}

	
	public List<Product> getProductsBySeller(Principal principal) {
        String username = principal.getName();
        Seller seller = sellerRepository.getSellerByUsername(username);
        return productRepository.findProductsBySellerId(seller.getId());
    }

/*
	public List<Product> getAllProducts() {
		
		return productRepository.findAll();
	}
*/
	
	// pagination 
	
	public List<Product> getAllProducts(int page ,int size) {
			
		// activate tha pageable interface
		 Pageable pageable = PageRequest.of(page, size);
		 // Call findAll inbuilt method as pass this pageable interface ref
			return productRepository.findAll(pageable).getContent();
		}
	


	public Product getProductById(int id) {
		
		return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product id is not found"));
	}



	public Product updateProduct(int id, Product product) {
		
		// validate the id and fetch the record from db
		Product dbProduct=productRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Invalid Product Id"));
		
		//update the product only for non-null fields
		if(product.getTitle() !=null)
			dbProduct.setTitle(product.getTitle());
		
		if(product.getDescription()!=null)
			dbProduct.setDescription(product.getDescription());

		 if (product.getMrpPrice() != 0)
		        dbProduct.setMrpPrice(dbProduct.getMrpPrice());

		if(product.getSellingPrice()!=0)
			dbProduct.setSellingPrice(product.getSellingPrice());

		if(product.getDiscountPercent()!=0)
			dbProduct.setDiscountPercent(product.getDiscountPercent());

		if(product.getQuantity() !=0)
			dbProduct.setQuantity(product.getQuantity());

		if(product.getColor()!=null)
			dbProduct.setColor(product.getColor());
		
		return productRepository.save(dbProduct);
	}



	public void deleteProductById(int id) {
		productRepository.deleteById(id);;
		
	}

}
