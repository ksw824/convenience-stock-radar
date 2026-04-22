package org.example.convradar.stockTest;

import org.example.convradar.domain.order.service.OrderService;
import org.example.convradar.domain.stock.entity.Stock;
import org.example.convradar.domain.stock.repository.StockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StockConcurrencyTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockRepository stockRepository;

    private Long stockId;

    @BeforeEach
    void setUp() {
        Stock stock = Stock.builder()
                .itemName("두쫀쿠")
                .quantity(100)
                .build();
        stockId = stockRepository.save(stock).getId();
    }

    @Test
    @DisplayName("100명이 동시에 1개씩 예약하면 재고가 정확히 0이 되어야 한다")
    void concurrencyTest() throws InterruptedException {
        int threadCount = 100;
        // 비동기 실행을 위한 쓰레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        // 모든 쓰레드가 끝날 때까지 대기하기 위한 래치
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.reserveItem(stockId, 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 요청이 끝날 때까지 대기

        // 결과 검증
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        // 100개에서 100명이 하나씩 가져갔으므로 정확히 0이어야 함
        assertEquals(0, stock.getQuantity());
    }
}