package com.example.backend.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //  256-bit Base64 encoded secret (HS256 safe)
    private static final String SECRET_KEY =
            "rJ7p7+2qTqZc5Y5gM7KZ9Z1uX8wE9c2vLZJxR7m6P9Y=";

    private static final long JWT_TOKEN_VALIDITY =
            5 * 60 * 60 * 1000; // 5 hours

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(SECRET_KEY)
        );
    }

    //  Generate JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //  Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    //  Validate JWT
    public boolean validateToken(String token, String username) {
        try {
            Claims claims = extractAllClaims(token);
            return username.equals(claims.getSubject())
                    && !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //  Internal claim parser
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
