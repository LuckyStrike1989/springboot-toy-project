package com.example.couponsystemapi.controller;

import com.example.couponsystemapi.service.ApplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {
    private final ApplyService applyService;

    public CouponController(ApplyService applyService) {
        this.applyService = applyService;
    }

    /**
     * /api/v1/coupons/test
     * @return
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        for(int i = 0; i <= 100; i++) {
            applyService.apply(i + 1L);
        }
        return ResponseEntity.ok("test");
    }
}
