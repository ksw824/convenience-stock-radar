package org.example.convradar.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 1. 헤더에서 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // 2. 토큰이 유효한지 JwtProvider에게 물어봄
        if (token != null && jwtProvider.validateToken(token)) {
            // 3. 토큰이 맞으면 유저 정보를 꺼내서 '인증 패스' 도장을 찍어줌
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        // 다음 검문소(Filter)로 이동
        chain.doFilter(request, response);
    }

    // 헤더의 "Authorization: Bearer [Token]" 부분에서 토큰만 쏙 빼오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}