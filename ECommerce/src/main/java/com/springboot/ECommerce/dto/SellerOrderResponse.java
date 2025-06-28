package com.springboot.ECommerce.dto;



import java.time.LocalDateTime;

import com.springboot.ECommerce.domain.OrderStatus;

public class SellerOrderResponse {
    private String customerName;
    private String customerEmail;
    private String productTitle;
    private int quantity;
    private double price;
    private OrderStatus status;
    private String customerStreet;
    private String customerCity;
    private String customerState;
    private String customerZipCode;
    private LocalDateTime orderDate;

    public SellerOrderResponse() {
        
    }

   
    public SellerOrderResponse(String customerName, String customerEmail, String productTitle, int quantity,
			double price, OrderStatus status, String customerStreet, String customerCity, String customerState,
			String customerZipCode, LocalDateTime orderDate) {
		super();
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.productTitle = productTitle;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.customerStreet = customerStreet;
		this.customerCity = customerCity;
		this.customerState = customerState;
		this.customerZipCode = customerZipCode;
		this.orderDate = orderDate;
	}




	public String getCustomerName() {
		return customerName;
	}




	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}




	public String getCustomerEmail() {
		return customerEmail;
	}




	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}




	public String getProductTitle() {
		return productTitle;
	}




	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}




	public int getQuantity() {
		return quantity;
	}




	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public OrderStatus getStatus() {
		return status;
	}




	public void setStatus(OrderStatus status) {
		this.status = status;
	}




	public String getCustomerStreet() {
		return customerStreet;
	}




	public void setCustomerStreet(String customerStreet) {
		this.customerStreet = customerStreet;
	}




	public String getCustomerCity() {
		return customerCity;
	}




	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}




	public String getCustomerState() {
		return customerState;
	}




	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}




	public String getCustomerZipCode() {
		return customerZipCode;
	}




	public void setCustomerZipCode(String customerZipCode) {
		this.customerZipCode = customerZipCode;
	}




	public LocalDateTime getOrderDate() {
		return orderDate;
	}




	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
    
    

	
}
