package org.example.convradar.domain.stock.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.convradar.domain.store.entity.Store;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Stock(String itemName, Integer quantity, Store store) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.store = store;
    }

    // 재고 변경 로직
    public void decrease(int qty) {
        if (this.quantity < qty) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        this.quantity -= qty;
    }
}