package com.example.couponsystemapi.service;

import com.example.couponsystemapi.repository.CouponCountRepository;
import com.example.couponsystemapi.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Pageable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
public class ApplyServiceTest {
    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponCountRepository couponCountRepository;

    @Test
    public void 한번만응모() {
        applyService.apply(1l);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 여러명응모() throws InterruptedException {
        // 레이스 컨디션 때문에 문제 발생!! 100개만 쿠폰 생성되어야 하는데, 테스트에서 116개 생성 => 두개이상의 쓰레드가 한개의 자원에 접근 할때 발생!
        // 레이스 컨디션에 대해 확인 필요 할뜻!!
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.execute(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();

        assertThat(count).isEqualTo(100);
    }
}


