package com.springboot.ECommerce.dto;

import com.springboot.ECommerce.model.CartItem;

public class CartItemResponse {
    private int customerId;
    private int productId;
    private String productTitle;
    private int sellingPrice;
    private int quantity;
    private int totalPrice;

    public CartItemResponse() {}

    public CartItemResponse(int customerId, int productId, String productTitle, int sellingPrice, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.totalPrice = sellingPrice * quantity;
    }

    public static CartItemResponse convertToDto(CartItem item) {
        return new CartItemResponse(
                item.getCart().getCustomer().getId(),
                item.getProduct().getId(),
                item.getProduct().getTitle(),
                item.getProduct().getSellingPrice(),
                item.getQuantity()
        );
    }

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

    
}
