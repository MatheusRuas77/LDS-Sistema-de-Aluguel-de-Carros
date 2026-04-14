package com.carrental.controller;

import com.carrental.model.Agente;
import com.carrental.service.AgenteService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller("/agentes")
public class AgenteController {

    private final AgenteService agenteService;

    @Inject
    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public Agente create(@Body @Valid Agente agente) {
        return agenteService.create(agente);
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Agente> findAll() {
        return agenteService.findAll();
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Agente findById(@PathVariable Long id) {
        return agenteService.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Agente não encontrado com id: " + id));
    }

    @Put("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Agente update(@PathVariable Long id, @Body @Valid Agente updated) {
        return agenteService.update(id, updated);
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agenteService.delete(id);
    }
}