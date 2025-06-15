package com.springboot.ECommerce.model;

import com.springboot.ECommerce.domain.CouponType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    private double discountPercentage;

    private boolean isActive;

    
    @ManyToOne
    private Customer customer;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public CouponType getCouponType() {
		return couponType;
	}


	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}


	public double getDiscountPercentage() {
		return discountPercentage;
	}


	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



}
