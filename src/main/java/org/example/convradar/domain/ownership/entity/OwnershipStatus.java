package org.example.convradar.domain.ownership.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnershipStatus {
    PENDING("대기", "관리자의 승인을 기다리고 있습니다."),
    APPROVED("승인", "매장 소유권이 확인되었습니다."),
    REJECTED("거절", "소유권이 확인 불가능합니다.");

    private final String title;
    private final String description;
}