package com.example.WebApplicationDesign.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${com.example.design.config.secret}")
    private String secret;
    @Value("${com.example.design.config.jwt.access-expiration}")
    private long expirationAccessToken;
    @Value("${com.example.design.config.jwt.refresh-expiration}")
    private long expirationRefreshToken;

    public String generateAccessToken(Integer id, String role) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationAccessToken))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public String generateRefreshToken(Integer id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationRefreshToken))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
