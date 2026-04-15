package com.carrental.controller;

import com.carrental.dto.PedidoRequest;
import com.carrental.enums.StatusPedidoEnum;
import com.carrental.model.Pedido;
import com.carrental.service.PedidoService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
@Tag(name = "Pedido")
@Controller("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    @Inject
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Post
    public HttpResponse<Pedido> create(
            @QueryValue Long clienteId,
            @Body PedidoRequest request) {

        Pedido pedido = new Pedido();
        pedido.setDataInicio(request.getDataInicio());
        pedido.setDataFim(request.getDataFim());

        Pedido salvo = pedidoService.create(
                clienteId,
                pedido,
                request.getAutomovelIds()
        );

        return HttpResponse.created(salvo);
    }

    @Get
    public HttpResponse<List<Pedido>> findByCliente(
            @QueryValue Long clienteId) {

        return HttpResponse.ok(pedidoService.findByCliente(clienteId));
    }

    @Put("/{id}/status")
    public HttpResponse<Pedido> atualizarStatus(
            @PathVariable Long id,
            @QueryValue Long agenteId,
            @Body Map<String, String> body) {

        StatusPedidoEnum novoStatus = StatusPedidoEnum.valueOf(body.get("status"));

        Pedido atualizado = pedidoService.atualizarStatus(id, novoStatus, agenteId);

        return HttpResponse.ok(atualizado);
    }
}