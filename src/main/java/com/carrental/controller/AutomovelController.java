package com.carrental.controller;

import com.carrental.model.Automovel;
import com.carrental.service.AutomovelService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
@Tag(name = "Automovel")
@Controller("/automovel")
public class AutomovelController {

    private final AutomovelService service;

    @Inject
    public AutomovelController(AutomovelService service) {
        this.service = service;
    }

    @Post
    public HttpResponse<Automovel> create(@Body @Valid Automovel automovel) {
        return HttpResponse.created(service.create(automovel));
    }

    @Get
    public Iterable<Automovel> findAll() {
        return service.findAll();
    }

    @Get("/{id}")
    public HttpResponse<Automovel> findById(Long id) {
        return service.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Put("/{id}")
    public HttpResponse<Automovel> update(Long id, @Body @Valid Automovel updated) {
        try {
            return HttpResponse.ok(service.update(id, updated));
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound();
        }
    }

    @Delete("/{id}")
    public HttpResponse<?> delete(Long id) {
        try {
            service.delete(id);
            return HttpResponse.noContent();
        } catch (IllegalArgumentException e) {
            return HttpResponse.notFound();
        }
    }
}