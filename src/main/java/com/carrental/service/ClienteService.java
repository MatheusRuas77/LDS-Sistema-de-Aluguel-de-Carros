package com.carrental.service;

import com.carrental.model.Cliente;
import com.carrental.model.Pedido;
import com.carrental.repository.ClienteRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Singleton
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente create(@Valid @NotNull Cliente cliente) {

    if (cliente.getRendimentos() != null) {
        cliente.getRendimentos().forEach(j -> j.setCliente(cliente));
    }
        //logica
        return repository.save(cliente);
    }

    public Iterable<Cliente> findAll() {
        return repository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Cliente update(Long id, @Valid Cliente updated) {
        Cliente existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id: " + id));

        existente.setNome(updated.getNome());
        existente.setLogin(updated.getLogin());
        existente.setSenha(updated.getSenha());
        existente.setEndereco(updated.getEndereco());
        existente.setCpf(updated.getCpf());
        existente.setRg(updated.getRg());
        existente.setProfissao(updated.getProfissao());

        return repository.update(existente);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
}