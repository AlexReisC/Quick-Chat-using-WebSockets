package com.alex.user_service.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSceret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(jwtSceret.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(getSigninKey())
            .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser()
            .verifyWith(getSigninKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token Inválido: " + e.getMessage());
            return false;
        }
    }
}
