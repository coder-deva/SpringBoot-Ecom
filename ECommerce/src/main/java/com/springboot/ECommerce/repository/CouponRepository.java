package com.springboot.ECommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.model.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Optional<Coupon> findByCouponType(CouponType type);
}


