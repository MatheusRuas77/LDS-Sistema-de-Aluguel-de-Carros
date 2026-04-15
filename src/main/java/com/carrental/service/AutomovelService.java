package com.carrental.service;

import com.carrental.model.Automovel;
import com.carrental.repository.AutomovelRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Singleton
public class AutomovelService {

    private final AutomovelRepository repository;

    public AutomovelService(AutomovelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Automovel create(@Valid Automovel automovel) {
        return repository.save(automovel);
    }

    public Iterable<Automovel> findAll() {
        return repository.findAll();
    }

    public Optional<Automovel> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Automovel update(Long id, @Valid Automovel updated) {
        Automovel existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Automovel não encontrado com id: " + id));

        existente.setMatricula(updated.getMatricula());
        existente.setAno(updated.getAno());
        existente.setModelo(updated.getModelo());
        existente.setPlaca(updated.getPlaca());
        existente.setMarca(updated.getMarca());
        if (updated.getProprietario() != null) existente.setProprietario(updated.getProprietario());

        return repository.update(existente);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Automovel não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
    public List<Automovel> buscarPorIds(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }
}