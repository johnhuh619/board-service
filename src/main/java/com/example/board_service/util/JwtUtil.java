package com.example.board_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // Base64 디코딩된 SecretKey 생성
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // Base64 디코딩
        this.secretKey = Keys.hmacShaKeyFor(keyBytes); // SecretKey 객체로 변환
    }

    // JWT 토큰 생성
    public String generateToken(String username) {
        long expirationMillis = 1000 * 60 * 60; // 1시간

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256) // SecretKey 사용
                .compact();
    }

    // 토큰에서 username 추출
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰 검증: (만료 여부 & username 일치 여부)
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // JWT Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // SecretKey 사용
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}