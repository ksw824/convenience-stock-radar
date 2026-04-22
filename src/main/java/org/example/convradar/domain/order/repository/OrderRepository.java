package org.example.convradar.domain.order.repository;

import org.example.convradar.domain.order.entity.Order;
import org.example.convradar.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusAndReservedAtBefore(OrderStatus status, LocalDateTime reservedAt);
}
