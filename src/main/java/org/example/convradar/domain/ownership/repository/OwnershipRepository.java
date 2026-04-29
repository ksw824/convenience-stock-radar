package org.example.convradar.domain.ownership.repository;

import org.example.convradar.domain.ownership.entity.Ownership;
import org.example.convradar.domain.ownership.entity.OwnershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnershipRepository extends JpaRepository<Ownership, Long> {
    List<Ownership> findAllByMemberId(Long memberId);
    List<Ownership> findAllByStatus(OwnershipStatus status);
}