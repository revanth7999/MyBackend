package com.backend.MyBackend.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil{
    private static final String SECRET_KEY = "YaKC9gQGqvd2mZMpBqXt8llaCDjdIyr5fcrom9i/K8E="; // Use a securely stored
    // production
    private static final long EXPIRATION_MILLIS = 15 * 60 * 1000; // 15 minutes

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static Key getSigningKey(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String subject,String role){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles",List.of(role)) // <- Add roles here
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRATION_MILLIS))
                .signWith(getSigningKey())
                .compact();
    }

    public static String generateRefreshToken(String subject){
        long now = System.currentTimeMillis();
        long refreshExpiration = 7 * 24 * 60 * 60 * 1000; // 7 days
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + refreshExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e){
            log.error("Token validation failed: {}",e.getMessage());
            return false; // Token is invalid or expired
        }
    }

    public static String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
