package org.example.convradar.domain.store.service;


import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.store.dto.StoreResponseDto;
import org.example.convradar.domain.store.entity.Store;
import org.example.convradar.domain.store.repository.StoreRepository;
import org.example.convradar.domain.store.repository.StoreWithDistance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getNearbyInventory(double lat, double lon) {
        List<StoreWithDistance> results = storeRepository.findNearbyStores(lat, lon);

        return results.stream().map(s -> {
            Store store = storeRepository.findById(s.getId()).orElseThrow();

            return StoreResponseDto.builder()
                    .storeName(s.getStoreName())
                    .brandName(s.getBrandName())
                    .address(s.getAddress())
                    .latitude(s.getLatitude())
                    .longitude(s.getLongitude())
                    .distance(s.getDistance())
                    .stocks(store.getStocks().stream()
                            .map(stock -> StoreResponseDto.StockDto.builder()
                                    .itemName(stock.getItemName())
                                    .quantity(stock.getQuantity())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());
    }

    public List<StoreResponseDto> searchStores(String keyword, String city){
        List<Store> stores = storeRepository.findByStoreNameContainingAndCityNameContaining(keyword, city);
        return stores.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }
}