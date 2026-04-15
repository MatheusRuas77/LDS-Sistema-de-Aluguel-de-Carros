package com.carrental.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.Map;
@Tag(name= "Pages")
@Controller("/pages")
public class PageController {

    @Get(value = "/dashboard-agente", produces = MediaType.TEXT_HTML)
    @View("pages/dashboard-agente")
    public Map<String, Object> dashboardAgente() {
        return Collections.emptyMap();
    }

    @Get(value = "/dashboard-cliente", produces = MediaType.TEXT_HTML)
    @View("pages/dashboard-cliente")
    public Map<String, Object> dashboardCliente() {
        return Collections.emptyMap();
    }
}