package com.carrental.service;

import com.carrental.enums.StatusPedidoEnum;
import com.carrental.model.Agente;
import com.carrental.model.Automovel;
import com.carrental.model.Cliente;
import com.carrental.model.Contrato;
import com.carrental.model.Pedido;
import com.carrental.repository.AgenteRepository;
import com.carrental.repository.PedidoRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final AutomovelService automovelService;
    private final AgenteRepository agenteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteService clienteService,
                         AutomovelService automovelService,
                         AgenteRepository agenteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.automovelService = automovelService;
        this.agenteRepository = agenteRepository;
    }

    @Transactional
    public Pedido create(Long clienteId, @Valid Pedido pedido, List<Long> automovelIds) {
        if (pedido.getDataFim().isBefore(pedido.getDataInicio())) {
            throw new IllegalArgumentException("A data de devolução não pode ser anterior à data de retirada.");
        }
        Cliente cliente = clienteService.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedidoEnum.PENDENTE);

        if (automovelIds != null && !automovelIds.isEmpty()) {
            List<Automovel> automoveis = automovelService.buscarPorIds(automovelIds);
            if (automoveis.size() != automovelIds.size()) {
                throw new IllegalArgumentException("Um ou mais automóveis não existem");
            }
            for (Automovel a : automoveis) {
                long conflitos = pedidoRepository.countAlugueisConflitantes(
                        a.getId(),
                        pedido.getDataInicio(),
                        pedido.getDataFim()
                );
                if (conflitos > 0) {
                    throw new IllegalArgumentException(
                            "Automóvel ID " + a.getId() + " já está reservado nesse período"
                    );
                }
            }
            pedido.setAutomoveis(automoveis);
        }
        return pedidoRepository.save(pedido);
    }


    public List<Pedido> findByCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findAll() {
        List<Pedido> result = new ArrayList<>();
        pedidoRepository.findAll().forEach(result::add);
        return result;
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido atualizarStatus(Long pedidoId, StatusPedidoEnum novoStatus, Long agenteId) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        pedido.setStatus(novoStatus);

        if (agenteId != null) {
            Agente agente = agenteRepository.findById(agenteId)
                    .orElseThrow(() -> new IllegalArgumentException("Agente não encontrado"));
            pedido.setAgenteResponsavel(agente);
        }

        if (novoStatus == StatusPedidoEnum.APROVADO) {
            Contrato contrato = new Contrato();
            contrato.setDataInicio(LocalDateTime.now());
            contrato.setPedido(pedido);
            pedido.setContrato(contrato);
        }

        return pedidoRepository.update(pedido);
    }
}