package org.example.convradar.domain.ownership.service;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.ownership.dto.OwnershipResponse;
import org.example.convradar.domain.ownership.entity.Ownership;
import org.example.convradar.domain.ownership.entity.OwnershipStatus;
import org.example.convradar.domain.ownership.repository.OwnershipRepository;
import org.example.convradar.global.error.BusinessException;
import org.example.convradar.global.error.ErrorCode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminOwnershipService {
    private final OwnershipRepository ownershipRepository;

    //모든 소유권 신청 내역 조회 (최신순)
    public List<OwnershipResponse> getAllOwnerships() {
        return ownershipRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(OwnershipResponse::from) // DTO 변환
                .toList();
    }

    //승인 대기 중인 신청만 조회
    public List<OwnershipResponse> getPendingOwnerships() {
        return ownershipRepository.findAllByStatus(OwnershipStatus.PENDING)
                .stream()
                .map(OwnershipResponse::from)
                .toList();
    }

    //승인 처리
    @Transactional
    public void approve(Long ownershipId) {
        Ownership ownership = ownershipRepository.findById(ownershipId)
                .orElseThrow(() -> new BusinessException(ErrorCode.OWNERSHIP_NOT_FOUND));
        
        ownership.approve();
    }

    @Transactional
    public void reject(Long ownershipId) {
        Ownership ownership = ownershipRepository.findById(ownershipId)
                .orElseThrow(() -> new BusinessException(ErrorCode.OWNERSHIP_NOT_FOUND));

        ownership.reject();
    }
}