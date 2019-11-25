package com.pyikhine.todolist.security;

import com.pyikhine.todolist.entities.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Generate Jwt token
// Validate the token
// Get user id from token
@Component
public class JwtTokenProvider {
    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + SecurityConstants.TOKEN_EXPIRATION_TIME);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", Long.toString(user.getId()));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature. SignatureException");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT Signature. MalformedJwtException");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token. UnsupportedJwtException");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();

        return Long.parseLong((String) claims.get("id"));
    }
}
