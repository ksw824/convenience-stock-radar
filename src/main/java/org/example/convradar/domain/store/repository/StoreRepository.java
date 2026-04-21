package org.example.convradar.domain.store.repository;

import org.example.convradar.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "SELECT s.id, s.store_name as storeName, s.brand_name as brandName, " +
            "s.address, s.latitude, s.longitude, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:lon)) + sin(radians(:lat)) * " +
            "sin(radians(s.latitude)))) AS distance " +
            "FROM store s " +
            "HAVING distance <= 2.0 " + // 2km 반경
            "ORDER BY distance", nativeQuery = true)
    List<StoreWithDistance> findNearbyStores(@Param("lat") double lat, @Param("lon") double lon);

}