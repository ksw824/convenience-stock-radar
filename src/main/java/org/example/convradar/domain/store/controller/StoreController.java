package org.example.convradar.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.store.dto.StoreResponseDto;
import org.example.convradar.domain.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/nearby")
    public ResponseEntity<List<StoreResponseDto>> getNearbyStores(
            @RequestParam double lat,
            @RequestParam double lon) {
        return ResponseEntity.ok(storeService.getNearbyInventory(lat, lon));
    }
}