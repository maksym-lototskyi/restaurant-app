package edu.pjatk.tin.restaurant.infrastructure.web.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(CustomUserPrincipal userDetails){
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.builder()
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setSubject(userDetails.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();
    }

    public UUID getId(String token) {
        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        return UUID.fromString(Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    private void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            logger.error("Token expired at: {}", e.getClaims().getExpiration());
            throw e;
        } catch (JwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}