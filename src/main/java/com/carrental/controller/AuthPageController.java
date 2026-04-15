package com.carrental.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import java.util.Collections;
import java.util.Map;

@Controller("/auth")
public class AuthPageController {

    @Get(value = "/login", produces = MediaType.TEXT_HTML)
    @View("auth/login")
    public Map<String, Object> loginPage() {
        return Collections.emptyMap();
    }
}

