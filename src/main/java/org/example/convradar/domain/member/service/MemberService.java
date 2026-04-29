package org.example.convradar.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.member.dto.LoginRequest;
import org.example.convradar.domain.member.dto.LoginResponse;
import org.example.convradar.domain.member.dto.MemberJoinRequest;
import org.example.convradar.domain.member.entity.Member;
import org.example.convradar.domain.member.repository.MemberRepository;
import org.example.convradar.global.error.BusinessException;
import org.example.convradar.global.error.ErrorCode;
import org.example.convradar.global.security.JwtProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public Long join(MemberJoinRequest request){
        if(memberRepository.existsByEmail(request.getEmail())){
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        // 2. 비밀번호 검증 (암호화된 비번과 대조)
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        // 3. 신분증(JWT) 발급
        String token = jwtProvider.createToken(member.getEmail(), member.getRole());

        return new LoginResponse(token, "Bearer");
    }
}
