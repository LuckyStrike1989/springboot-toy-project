package com.example.couponsystemapi.service;

import com.example.couponsystemapi.domain.Coupon;
import com.example.couponsystemapi.producer.CouponCreateProducer;
import com.example.couponsystemapi.repository.CouponCountRepository;
import com.example.couponsystemapi.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId) {
        // 레이스 컨디션을 해결하기위해 자바 싱크로나이즈드를 생각 할수도 있지만, 서버가 여러대면 다시 레이스 컨디션 발생! 다른방법 생각
        // redis incr 명령어 존재 -> key:value에서 value를 1씩 증가 시켜주는 명령어
        // redis는 싱글 쓰레드로 돌기때문에, 레이스 컨티션 해결 가능, 성능도 up

        /*
        카프카란?
        분산 이벤트 스트리밍 플랫폼.
        이벤트 스트리밍이란 소스에서 목적기까지 이벤트를 실시간으로 스트리밍 하는것
         */

        // long count = couponRepository.count();
        Long count = couponCountRepository.increment();

        if( count > 100 ) {
            return;
        }

        couponRepository.save(new Coupon(userId));
        couponCreateProducer.create(userId);
    }
}
