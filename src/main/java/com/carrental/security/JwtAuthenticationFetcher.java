package com.carrental.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;

@Singleton
public class JwtAuthenticationFetcher implements AuthenticationFetcher<HttpRequest<?>> {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFetcher(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {

        System.out.println(">>> FETCHER CHAMADO");

        String authHeader = request.getHeaders().get("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            if (jwtUtil.tokenValido(token)) {

                var claims = jwtUtil.validarToken(token);

                String login = claims.getSubject();
                String role = claims.get("role", String.class);

                System.out.println(">>> TOKEN VALIDO: " + login + " ROLE: " + role);

                List<String> roles = (role != null) ? List.of(role) : List.of();

                return Flowable.just(
                        Authentication.build(
                                login,
                                roles
                        )
                );
            }
        }

        return Flowable.empty();
    }
}