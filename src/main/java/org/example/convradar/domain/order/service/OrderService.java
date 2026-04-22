package org.example.convradar.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.order.entity.Order;
import org.example.convradar.domain.order.repository.OrderRepository;
import org.example.convradar.domain.stock.entity.Stock;
import org.example.convradar.domain.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Transactional
    public Long reserveItem(Long stockId, int quantity) {
        Stock stock = stockRepository.findByIdWithLock(stockId)
                .orElseThrow(()-> new RuntimeException("상품이 존재하지 않습니댜 (stockId : " + stockId + ")"));

        System.out.println(Thread.currentThread().getName() + " - lock 획득");

        stock.decrease(quantity);

        try {
            Thread.sleep(5000); // 5초 동안 락 쥐고 있기
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Order order = Order.builder()
                .stock(stock)
                .quantity(quantity)
                .build();

        System.out.println(Thread.currentThread().getName() + " - 작업 종료");

        return orderRepository.save(order).getId();
    }

}
