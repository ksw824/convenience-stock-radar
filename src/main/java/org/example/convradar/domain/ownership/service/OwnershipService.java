package org.example.convradar.domain.ownership.service;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.member.entity.Member;
import org.example.convradar.domain.member.repository.MemberRepository;
import org.example.convradar.domain.ownership.entity.Ownership;
import org.example.convradar.domain.ownership.repository.OwnershipRepository;
import org.example.convradar.domain.store.entity.Store;
import org.example.convradar.domain.store.repository.StoreRepository;
import org.example.convradar.global.error.BusinessException;
import org.example.convradar.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnershipService {
    private final OwnershipRepository ownershipRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public Long requestOwnership(String email, Long storeId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Ownership ownership = Ownership.builder()
                .member(member)
                .store(store)
                .build();

        return ownershipRepository.save(ownership).getId();
    }
}