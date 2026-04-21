package org.example.convradar.domain.store.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.convradar.domain.stock.entity.Stock;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String brandName;
    private String address;
    private Double latitude;    //위도
    private Double longitude;   //경도

    @Builder
    public Store(String storeName, String brandName, String address, Double latitude, Double longitude) {
        this.storeName = storeName;
        this.brandName = brandName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @OneToMany(mappedBy = "store")
    private List<Stock> stocks = new ArrayList<>();

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", stocks=" + stocks +
                '}';
    }
}
