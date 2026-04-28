package org.example.convradar.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.member.dto.MemberJoinRequest;
import org.example.convradar.domain.member.entity.Member;
import org.example.convradar.domain.member.repository.MemberRepository;
import org.example.convradar.global.error.BusinessException;
import org.example.convradar.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberJoinRequest request){
        if(memberRepository.existsByEmail(request.getEmail())){
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .role(request.getRole())
                .build();

        return memberRepository.save(member).getId();
    }
}
