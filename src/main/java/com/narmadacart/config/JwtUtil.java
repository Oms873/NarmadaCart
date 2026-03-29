package com.narmadacart.config;

import com.narmadacart.entity.User;
import com.narmadacart.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class JwtUtil {

       private final String SECRET_KEY = "narmadacartnarmadacartnarmadacart12"; // minimum 32 characters for HS256

       private Key getSigningKey() {
           return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
       }
       @Autowired
       private UserRepository userRepository;
       public String generateToken(User user) {
           return Jwts.builder()
                   .claim("role",user.getRole().name())
                   .claim("userId", user.getId())//roles added in claims
                   .setSubject(user.getEmail()) // email as subject
                   .setIssuedAt(new Date())
                     .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                   .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                   .compact();
       }

       public Claims getClaims(String token) {
           return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
       }

       public String extractUserName(String token) {
           return getClaims(token).getSubject();
       }

       public Long extractUserId(String token) {
           return getClaims(token).get("userId", Long.class);
       }
       public boolean isTokenExpired(String token) {
           return getClaims(token).getExpiration().before(new Date());
       }

       public boolean validateToken(String token, String userName) {
           String extractedUserName = extractUserName(token);
           return (extractedUserName.equals(userName) && !isTokenExpired(token));
       }

       public String extractRoles(String token) {
           return getClaims(token).get("role", String.class);
       }
}
