package org.example.convradar.domain.ownership.controller;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.ownership.service.OwnershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ownership")
@RequiredArgsConstructor
public class OwnershipController {
    private final OwnershipService ownershipService;

    @PostMapping("/request/{storeId}")
    public ResponseEntity<Long> requestOwnership(
            @AuthenticationPrincipal String email,
            @PathVariable Long storeId) {
        return ResponseEntity.ok(ownershipService.requestOwnership(email, storeId));
    }
}