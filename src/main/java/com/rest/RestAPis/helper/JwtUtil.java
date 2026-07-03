/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest.RestAPis.helper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pravin Prajapati
 */
@Component
public class JwtUtil {
    
    
    @Value("${jwt.secret}")
    private String SECRET;
    public String generateToken(String email, String role) {

    return Jwts.builder()
            .claim("role", role)
            .subject(email)
            .issuedAt(new Date())
            .expiration(
                new Date(System.currentTimeMillis() + 1000 * 60 * 60)
            )
            .signWith(
                Keys.hmacShaKeyFor(SECRET.getBytes())
            )
            .compact();
}
    
   
    
    
    public String extractEmail(String token) {
    return Jwts.parser()
            .verifyWith(
                    Keys.hmacShaKeyFor(
                            SECRET.getBytes()
                    )
            )
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
    
    public String extractRole(String token) {

    return Jwts.parser()
            .verifyWith(
                Keys.hmacShaKeyFor(SECRET.getBytes())
            )
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String.class);
}
    
    public boolean validateToken(String token) {
    try {
        Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        )
                )
                .build()
                .parseSignedClaims(token);

        return true;
    } catch (Exception e) {
        return false;
    }
}

   
    
}
