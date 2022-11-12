package com.samrt.dubai.book.store.repository;

import com.samrt.dubai.book.store.entity.Classification;
import com.samrt.dubai.book.store.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Optional<Coupon> findByClassification(Classification classification);

    Optional<Coupon> findByCouponCode(String couponCode);
}
