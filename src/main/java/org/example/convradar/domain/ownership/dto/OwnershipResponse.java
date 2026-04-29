package org.example.convradar.domain.ownership.dto;

import org.example.convradar.domain.ownership.entity.Ownership;
import org.example.convradar.domain.ownership.entity.OwnershipStatus;

import java.time.format.DateTimeFormatter;

public record OwnershipResponse(
        Long id,
        String memberEmail,
        String memberName,
        Long storeId,
        String storeName,
        String storeAddress,
        OwnershipStatus status,
        String createdAt
) {

    public static OwnershipResponse from(Ownership ownership) {
        return new OwnershipResponse(
            ownership.getId(),
            ownership.getMember().getEmail(),
            ownership.getMember().getName(),
            ownership.getStore().getId(),
            ownership.getStore().getStoreName(),
            ownership.getStore().getAddress(),
            ownership.getStatus(),
            ownership.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}