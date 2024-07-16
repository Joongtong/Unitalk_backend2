package com.unitalk.member.config;

import com.unitalk.member.jwt.JWTFilter;
import com.unitalk.member.jwt.JWTUtil;
import com.unitalk.member.jwt.LoginFilter;
import com.unitalk.member.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 보안 설정을 구성하는 클래스입니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * SecurityConfig 생성자입니다.
     *
     * @param authenticationConfiguration 인증 설정을 위한 AuthenticationConfiguration 객체
     * @param jwtUtil JWT 토큰 유틸리티 클래스
     * @param customUserDetailsService 사용자 세부 정보 서비스
     */
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * AuthenticationManager 빈을 생성합니다.
     *
     * @param configuration 인증 설정 객체
     * @return AuthenticationManager 객체
     * @throws Exception 예외 발생 시
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * BCryptPasswordEncoder 빈을 생성합니다.
     *
     * @return BCryptPasswordEncoder 객체
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 보안 필터 체인을 구성합니다.
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 객체
     * @throws Exception 예외 발생 시
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) -> auth.disable()); // CSRF 비활성화
        http.formLogin((auth) -> auth.disable()); // 폼 로그인 비활성화
        http.httpBasic((auth) -> auth.disable()); // HTTP 기본 인증 비활성화
        http.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/api/login", "/api/join", "/api/logout").permitAll() // 특정 경로 접근 허용
//                .requestMatchers("/admin").hasRole("ADMIN") // /admin 경로는 ADMIN 역할만 접근 허용
                .anyRequest().authenticated()); // 그 외 모든 요청은 인증 필요
        http.addFilterBefore(new JWTFilter(jwtUtil, customUserDetailsService), UsernamePasswordAuthenticationFilter.class); // JWT 필터 추가
        http.addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class); // 로그인 필터 추가
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 상태를 Stateless로 설정
        return http.build();
    }
}
