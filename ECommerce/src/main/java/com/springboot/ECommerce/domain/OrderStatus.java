package com.springboot.ECommerce.domain;

public enum OrderStatus {
	PLACED,
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;
	
	 @Override
	    public String toString() {
	        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	    }
    
    
}

