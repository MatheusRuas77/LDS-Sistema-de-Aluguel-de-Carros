package com.carrental.security;

import com.carrental.model.Agente;
import com.carrental.model.Cliente;
import com.carrental.model.Usuario;
import com.carrental.service.AgenteService;
import com.carrental.service.ClienteService;
import io.jsonwebtoken.Claims;
import jakarta.inject.Singleton;

@Singleton
public class AuthService {
    private final JwtUtil jwtUtil;
    private final ClienteService clienteService;
    private final AgenteService agenteService;

    public AuthService(JwtUtil jwtUtil,
                       ClienteService clienteService,
                       AgenteService agenteService) {
        this.jwtUtil = jwtUtil;
        this.clienteService = clienteService;
        this.agenteService = agenteService;
    }

    public Usuario buscarUsuarioPorToken(String authHeader) {

        String token = extrairToken(authHeader);
        Claims claims = jwtUtil.validarToken(token);

        Long userId = Long.valueOf(claims.get("id").toString());
        String tipo = claims.get("tipo", String.class);

        if ("CLIENTE".equalsIgnoreCase(tipo)) {
            return clienteService.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        }

        if ("AGENTE".equalsIgnoreCase(tipo)) {
            return agenteService.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Agente não encontrado"));
        }

        throw new IllegalArgumentException("Tipo de usuário inválido no token");
    }
    private String extrairToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Token inválido");
    }
}