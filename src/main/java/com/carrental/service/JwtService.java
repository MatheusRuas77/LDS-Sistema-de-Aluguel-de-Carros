package com.carrental.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.util.Date;

@Singleton
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 3600000;
    public String gerarToken(Long id, String login, String tipoUsuario, String role) {
        return Jwts.builder()
                .setSubject(login)
                .claim("id", id)
                .claim("tipoUsuario", tipoUsuario)
                .claim("role", role != null ? role : "CLIENTE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}