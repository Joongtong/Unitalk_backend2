package com.unitalk.member.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unitalk.member.dto.CustomUserDetails;
import com.unitalk.member.dto.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 로그인 요청을 처리하는 필터 클래스입니다.
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    /**
     * LoginFilter 생성자입니다.
     *
     * @param authenticationManager 인증을 처리하는 AuthenticationManager
     * @param jwtUtil JWT 토큰을 생성하는 유틸리티 클래스
     */
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 로그인 시도 요청을 처리합니다.
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @return 인증된 Authentication 객체
     * @throws AuthenticationException 인증 예외 발생 시
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("LoginFilter/로그인요청확인");

            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserId(),
                            loginRequest.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            System.out.println("LoginFilter/attemptAuthentication/IOException: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            System.out.println("LoginFilter/attemptAuthentication/AuthenticationException: 자격 증명에 실패하였습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"message\":\"자격 증명에 실패하였습니다.\"}");
                response.getWriter().flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return null;
        }
    }

    /**
     * 로그인 성공 시 호출됩니다.
     *
     * @param request       HttpServletRequest 객체
     * @param response      HttpServletResponse 객체
     * @param chain         FilterChain 객체
     * @param authentication 인증된 Authentication 객체
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = customUserDetails.getUsername();
        String role = customUserDetails.getAuthorities().iterator().next().getAuthority();
        String userType = customUserDetails.getUserType(); // CustomUserDetails에 userType을 추가해야 합니다.
        String token = jwtUtil.createJwt(username, role, userType, 60 * 60 * 10L);
        System.out.println("LoginFilter/로그인 성공 " + username + " " + role + " " + token);
        response.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * 로그인 실패 시 호출됩니다.
     *
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     * @param failed   인증 예외 객체
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        System.out.println("LoginFilter/로그인 실패");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"자격 증명에 실패하였습니다.\"}");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
