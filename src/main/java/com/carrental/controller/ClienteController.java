package com.carrental.controller;

import com.carrental.model.Cliente;
import com.carrental.model.Pedido;
import com.carrental.service.ClienteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }
    @Post
    public HttpResponse<Cliente> create(@Body @Valid Cliente cliente) {
        Cliente salvo = service.create(cliente);
        return HttpResponse.created(salvo);
    }

    @Get
    public Iterable<Cliente> findAll() {
        return service.findAll();
    }

    @Get("/{id}")
    public HttpResponse<Cliente> findById(Long id) {
        return service.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Put("/{id}")
    public HttpResponse<Cliente> update(Long id, @Body @Valid Cliente updated) {
        try {
            Cliente cliente = service.update(id, updated);
            return HttpResponse.ok(cliente);
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