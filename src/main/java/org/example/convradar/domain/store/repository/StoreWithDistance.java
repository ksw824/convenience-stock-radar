package org.example.convradar.domain.store.repository;

public interface StoreWithDistance {
    Long getId();
    String getStoreName();
    String getBrandName();
    String getAddress();
    Double getLatitude();
    Double getLongitude();
    Double getDistance();
}