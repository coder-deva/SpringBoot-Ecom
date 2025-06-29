package com.springboot.ECommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springboot.ECommerce.domain.CouponType;
import com.springboot.ECommerce.model.Coupon;
import com.springboot.ECommerce.repository.CouponRepository;
import com.springboot.ECommerce.service.CouponService;

public class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApplyCoupon_Success() {
        Coupon coupon = new Coupon();
        coupon.setCouponType(CouponType.FESTIVAL_DISCOUNT10);
        coupon.setDiscountPercentage(10);
        coupon.setActive(true);

        when(couponRepository.findByCouponType(CouponType.FESTIVAL_DISCOUNT10))
            .thenReturn(Optional.of(coupon));

        double result = couponService.applyCoupon(CouponType.FESTIVAL_DISCOUNT10, 1000);

        assertEquals(900, result);
    }

    @Test
    public void testApplyCoupon_InvalidCoupon() {
        when(couponRepository.findByCouponType(CouponType.FESTIVAL_DISCOUNT10))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            couponService.applyCoupon(CouponType.FESTIVAL_DISCOUNT10, 1000));
    }

    @Test
    public void testApplyCoupon_InactiveCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCouponType(CouponType.FESTIVAL_DISCOUNT10);
        coupon.setDiscountPercentage(10);
        coupon.setActive(false);

        when(couponRepository.findByCouponType(CouponType.FESTIVAL_DISCOUNT10))
            .thenReturn(Optional.of(coupon));

        assertThrows(RuntimeException.class, () ->
            couponService.applyCoupon(CouponType.FESTIVAL_DISCOUNT10, 1000));
    }
}
