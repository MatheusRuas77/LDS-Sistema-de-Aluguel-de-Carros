package com.carrental.service;

import com.carrental.exception.InvalidCredentialsException;
import com.carrental.model.Agente;
import com.carrental.repository.AgenteRepository;
import com.carrental.security.JwtUtil;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Singleton
public class AgenteService {

    private final AgenteRepository repository;
    private final JwtUtil jwtUtil;

    @Inject
    public AgenteService(AgenteRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public Agente create(@Valid @NotNull Agente agente) {
        if (agente.getSenha() != null && !agente.getSenha().isBlank()) {
            agente.setSenha(BCrypt.hashpw(agente.getSenha(), BCrypt.gensalt()));
        }
        return repository.save(agente);
    }

    public Iterable<Agente> findAll() {
        return repository.findAll();
    }

    public Optional<Agente> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Agente update(Long id, @Valid Agente updated) {
        Agente existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agente não encontrado com id: " + id));

        existente.setNome(updated.getNome());
        existente.setLogin(updated.getLogin());
        existente.setEndereco(updated.getEndereco());
        if (updated.getRole() != null) existente.setRole(updated.getRole());

        if (updated.getSenha() != null && !updated.getSenha().isBlank()) {
            existente.setSenha(BCrypt.hashpw(updated.getSenha(), BCrypt.gensalt()));
        }

        return repository.update(existente);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Agente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    public String autenticar(String login, String senha) {
        Agente agente = repository.findByLogin(login);

        if (agente == null) {
            throw new InvalidCredentialsException("Login ou senha invalidos");
        }

        if (!BCrypt.checkpw(senha, agente.getSenha())) {
            throw new InvalidCredentialsException("Login ou senha invalidos");
        }

        return jwtUtil.gerarToken(
                agente.getId(),
                agente.getLogin(),
                "AGENTE",
                agente.getRole().name()
        );
    }
}