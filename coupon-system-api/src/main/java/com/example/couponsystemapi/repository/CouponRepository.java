package com.example.couponsystemapi.repository;

import com.example.couponsystemapi.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
