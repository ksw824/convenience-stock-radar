package org.example.convradar.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.convradar.domain.member.entity.Member;
import org.example.convradar.domain.stock.entity.Stock;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;    // 전체 사업장명 (예: GS25청평점)
    private String brandName;    // 브랜드 (예: GS25)
    private String cityName;     // 검색 최적화용
    private String address;
    private Double latitude;
    private Double longitude;

    // 점주 연동 전에는 null
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Stock> stocks = new ArrayList<>();

    @Builder
    public Store(String storeName, String brandName, String cityName, String address, Double latitude, Double longitude) {
        this.storeName = storeName;
        this.brandName = brandName;
        this.cityName = cityName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void assignOwner(Member owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Store{" + "id=" + id + ", storeName='" + storeName + '\'' + '}';
    }
}