package com.carrental.service;

import com.carrental.model.Cliente;
import com.carrental.repository.ClienteRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Singleton
public class ClienteService {

    private final ClienteRepository repository;
    private final JwtService jwtService;

    @Inject
    public ClienteService(ClienteRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    @Transactional
    public Cliente create(@Valid @NotNull Cliente cliente) {
        if (cliente.getSenha() != null && !cliente.getSenha().isBlank()) {
            cliente.setSenha(BCrypt.hashpw(cliente.getSenha(), BCrypt.gensalt()));
        }

        if (cliente.getRendimentos() != null) {
            cliente.getRendimentos().forEach(j -> j.setCliente(cliente));
        }
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
        existente.setEndereco(updated.getEndereco());
        existente.setCpf(updated.getCpf());
        existente.setRg(updated.getRg());
        existente.setProfissao(updated.getProfissao());

        if (updated.getSenha() != null && !updated.getSenha().isBlank()) {
            existente.setSenha(BCrypt.hashpw(updated.getSenha(), BCrypt.gensalt()));
        }

        return repository.update(existente);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    public String autenticar(String login, String senha) {
        Cliente cliente = repository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        if (!BCrypt.checkpw(senha, cliente.getSenha())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return jwtService.gerarToken(
                cliente.getId(),
                cliente.getLogin(),
                "CLIENTE",
                null
        );
    }
}