package com.targetcoders.sowing.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtParserService {

    private final JwtParser jwtParser;

    public String memberRole(String token) {
        return (String) jwtParser.parseClaimsJws(token).getBody().get("role");
    }

    public String userPk(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    public String refreshToken(String token) {
        return (String) jwtParser.parseClaimsJws(token).getBody().get("rt");
    }

    public boolean isValidateToken(String token) {
        try {
            Jws<Claims> claims = jwtParser.parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
