package com.springboot.ECommerce.dto;

import java.util.List;

import com.springboot.ECommerce.model.Product;


// to post the category using the name
public class ProductRequest {

    private Product product;

    private List<String> categoryNames;
    
//    private List<Integer> categoryIds; using category ids

    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }
}
