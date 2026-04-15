package com.carrental.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.Map;
@Tag(name = "AuthPage")
@Controller("/auth")
public class AuthPageController {

    @Get(value = "/login", produces = MediaType.TEXT_HTML)
    @View("auth/login")
    public Map<String, Object> loginPage() {
        return Collections.emptyMap();
    }
    @Get(value = "/dashboard-agente", produces = MediaType.TEXT_HTML)
    @View("auth/dashboard-agente")
    public Map<String, Object> dashboardAgentePage() {
        return Collections.emptyMap();
    }
}

