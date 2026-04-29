package org.example.convradar.domain.ownership.admin;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.ownership.dto.OwnershipResponse;
import org.example.convradar.domain.ownership.service.AdminOwnershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ownership")
@RequiredArgsConstructor
public class AdminOwnershipController {
    private final AdminOwnershipService adminOwnershipService;

    // 전체 리스트 조회
    @GetMapping
    public ResponseEntity<List<OwnershipResponse>> getAllOwnerships() {
        return ResponseEntity.ok(adminOwnershipService.getAllOwnerships());
    }

    // 대기 중인 리스트만 조회
    @GetMapping("/pending")
    public ResponseEntity<List<OwnershipResponse>> getPendingOwnerships() {
        return ResponseEntity.ok(adminOwnershipService.getPendingOwnerships());
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Void> approve(@PathVariable Long id) {
        adminOwnershipService.approve(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        adminOwnershipService.reject(id);
        return ResponseEntity.ok().build();
    }
}