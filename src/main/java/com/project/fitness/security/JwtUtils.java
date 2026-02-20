package com.project.fitness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.security.Key;
import java.util.List;

@Component
public class JwtUtils {
    //helper to generate and validate jwt token


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationInSeconds;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateToken(String userId, String role) {


        return Jwts.builder()
                .subject(userId)
                .claim("roles", List.of(new SimpleGrantedAuthority(role)))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+jwtExpirationInSeconds))
                .signWith(key())
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey)key()).build()
                    .parseSignedClaims(token);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String jwt){
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt)
                .getPayload().getSubject();
    }

    public Claims getClaimsFromToken(String jwt){
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwt).getPayload();
    }

}
