package org.example.convradar.domain.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.convradar.domain.order.entity.Order;
import org.example.convradar.domain.order.entity.OrderStatus;
import org.example.convradar.domain.order.repository.OrderRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {
    private final OrderRepository orderRepository;

    @Scheduled(fixedDelay = 60000) // 1분마다
    @Transactional
    public void cancelExpiredOrders() {
        log.info("###[만료된 예약 확인 스케줄러 실행 중...]###");
        LocalDateTime limit = LocalDateTime.now().minusMinutes(1);

        List<Order> expiredOrders = orderRepository.findAllByStatusAndReservedAtBefore(OrderStatus.RESERVED, limit);

        for(Order order : expiredOrders) {
            order.cancel();
            order.getStock().increase(order.getQuantity());
            log.info("###[주문 ID {} 취소 및 재고 {}개 복구 완료 (상품: {})]###",
                    order.getId(), order.getQuantity(), order.getStock().getItemName());
        }
    }
}
