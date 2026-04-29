package org.example.convradar.domain.ownership.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.convradar.domain.member.entity.Member;
import org.example.convradar.domain.store.entity.Store;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ownership {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 신청한 사장님

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; // 대상 매장

    // PENDING, APPROVED, REJECTED
    @Enumerated(EnumType.STRING)
    private OwnershipStatus status;

    private LocalDateTime createdAt;

    @Builder
    public Ownership(Member member, Store store) {
        this.member = member;
        this.store = store;
        this.status = OwnershipStatus.PENDING; // 초기값은 대기중
        this.createdAt = LocalDateTime.now();
    }

    // 관리자가 승인 시 호출할 메서드 (핵심 비즈니스 로직)
    public void approve() {
        this.status = OwnershipStatus.APPROVED;
    }

    public void reject() {
        this.status = OwnershipStatus.REJECTED;
    }
}