package org.example.convradar.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.convradar.domain.stock.entity.Stock;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime reservedAt;   //예약 시간 (예약 만료 확인용)

    @Builder
    public Order(Stock stock, Integer quantity) {
        this.stock = stock;
        this.quantity = quantity;
        this.reservedAt = LocalDateTime.now();
        this.status = OrderStatus.RESERVED;
    }

    public void complete(){
        this.status = OrderStatus.COMPLETED;
    }

    public void cancel(){
        this.status = OrderStatus.CANCELED;
    }
}
