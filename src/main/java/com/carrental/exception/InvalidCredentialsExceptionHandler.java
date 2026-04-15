package com.carrental.exception;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.Map;

@Produces
@Singleton
public class InvalidCredentialsExceptionHandler
        implements ExceptionHandler<InvalidCredentialsException, HttpResponse<Map<String, Object>>> {

    @Override
    public HttpResponse<Map<String, Object>> handle(HttpRequest request, InvalidCredentialsException exception) {
        return HttpResponse.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.UNAUTHORIZED.getCode(),
                        "path", request.getPath()
                ));
    }
}

