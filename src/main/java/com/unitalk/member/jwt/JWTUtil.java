package com.unitalk.member.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    /**
     * JWTUtil 생성자. 주어진 시크릿 키를 사용하여 SecretKey 객체를 초기화합니다.
     *
     * @param secret JWT 토큰을 서명하는 데 사용할 시크릿 키
     */
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * 주어진 토큰에서 사용자 이름을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 토큰에 포함된 사용자 이름
     */
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    /**
     * 주어진 토큰에서 사용자 역할을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 토큰에 포함된 사용자 역할
     */
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    /**
     * 주어진 토큰이 만료되었는지 확인합니다.
     *
     * @param token JWT 토큰
     * @return 토큰이 만료되었으면 true, 그렇지 않으면 false
     */
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    /**
     * 새로운 JWT 토큰을 생성합니다.
     *
     * @param username 사용자 이름
     * @param role 사용자 역할
     * @param expiredSeconds 토큰의 만료 시간(초 단위)
     * @return 생성된 JWT 토큰
     */
    public String createJwt(String username, String role, String userType, Long expiredSeconds) {
        // 초를 밀리초로 변환합니다.
        long expiredMs = expiredSeconds * 1000;
        System.out.println("Util/expiredMs: " + expiredMs);
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .claim("userType", userType)
                .issuedAt(new Date(System.currentTimeMillis()))
                // 만료 시간을 설정합니다.
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
