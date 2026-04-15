package com.carrental.controller;

import com.carrental.service.ClienteService;
import com.carrental.service.AgenteService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.exceptions.HttpStatusException;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
@Tag(name = "Auth")
@Controller("/auth")
public class AuthController {

    private final ClienteService clienteService;
    private final AgenteService agenteService;

    public AuthController(ClienteService clienteService, AgenteService agenteService) {
        this.clienteService = clienteService;
        this.agenteService = agenteService;
    }

    @Post(value = "/login/cliente", produces = MediaType.APPLICATION_JSON)
    public Map<String, String> loginCliente(@Body Map<String, String> credentials) {
        String login = credentials.get("login");
        String senha = credentials.get("senha");

        if (login == null || senha == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Login e senha são obrigatórios");
        }

        String token = clienteService.autenticar(login, senha);
        return Map.of("token", token, "tipoUsuario", "CLIENTE");
    }

    @Post(value = "/login/agente", produces = MediaType.APPLICATION_JSON)
    public Map<String, String> loginAgente(@Body Map<String, String> credentials) {
        String login = credentials.get("login");
        String senha = credentials.get("senha");

        if (login == null || senha == null) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "Login e senha são obrigatórios");
        }

        String token = agenteService.autenticar(login, senha);
        return Map.of("token", token, "tipoUsuario", "AGENTE");
    }
}