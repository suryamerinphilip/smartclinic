package com.hospital.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class TokenValidationSeviceImp implements  TokenValidationService{
    private final String secretKey = "mySuperSecretKey"; // ideally from application.properties

    @Override
    public Map<String, Object> validateToken(String token, String expectedRole) {
        Map<String, Object> result = new HashMap<>();

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            if (!expectedRole.equalsIgnoreCase(role)) {
                result.put("error", "Invalid role");
            }

        } catch (JwtException | IllegalArgumentException e) {
            result.put("error", "Invalid token");
        }

        return result;
    }

}
