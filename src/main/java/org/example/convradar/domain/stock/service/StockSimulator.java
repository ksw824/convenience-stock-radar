package org.example.convradar.domain.stock.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.stock.entity.Stock;
import org.example.convradar.domain.stock.repository.StockRepository;
import org.example.convradar.domain.store.entity.Store;
import org.example.convradar.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockSimulator {
    private final StockRepository stockRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void initStock() {
        List<Store> stores = storeRepository.findAll();

        //임의로 편의점 물품 생성
        String[] targetItems = {"두바이 초콜릿", "아사히 생맥주", "요아정 요트"};

        for (Store store : stores) {
            for (String item : targetItems) {
                Stock stock = Stock.builder()
                        .itemName(item)
                        .quantity((int) (Math.random() * 6)) // 0~5개 랜덤 재고 생성
                        .store(store)
                        .build();
                stockRepository.save(stock);
            }
        }
    }
}