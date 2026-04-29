package org.example.convradar.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 방어 비활성화
            .httpBasic(AbstractHttpConfigurer::disable) // 브라우저 팝업 인증 비활성화
            .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인 페이지 비활성화
            .cors(cors -> cors.configurationSource(corsConfigurationSource)) // CORS 설정 연결

            // 2. 세션 정책 설정 (Stateless)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 서버에 세션을 만들지 않음
            )

            // 3. API 접근 권한 설정 (인가)
            .authorizeHttpRequests(auth -> auth
                // 회원가입, 로그인은 인증 없이 누구나 접근 가능
                .requestMatchers("/api/members/join", "/api/members/login").permitAll()
                
                // 관리자(ADMIN) 전용 API 경로 설정
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                
                // 그 외 모든 요청은 인증(JWT)이 필요함
                .anyRequest().authenticated()
            )

            // 4. JWT 필터 위치 설정
             .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}