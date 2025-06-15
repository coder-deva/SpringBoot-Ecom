package com.springboot.ECommerce.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.exception.ResourceNotFoundException;
import com.springboot.ECommerce.model.Coupon;
import com.springboot.ECommerce.repository.CouponRepository;


@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public double applyCoupon(CouponType couponType, double totalAmount) {
        Coupon coupon = couponRepository.findByCouponType(couponType)
                .orElseThrow(() -> new RuntimeException("Invalid coupon"));

        if (!coupon.isActive()) {
            throw new RuntimeException("Coupon is not active");
        }

        double discount = (coupon.getDiscountPercentage() / 100.0) * totalAmount;
        return totalAmount - discount;
    }
}
