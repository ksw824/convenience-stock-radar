package org.example.convradar.domain.store.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class StoreResponseDto {
    private String storeName; //지점명
    private String brandName; //편의점 브랜드
    private String address;
    private Double latitude;
    private Double longitude;
    private Double distance; // 키로미터
    private List<StockDto> stocks;

    @Getter
    @Builder
    public static class StockDto {
        private String itemName;
        private Integer quantity;
    }
}