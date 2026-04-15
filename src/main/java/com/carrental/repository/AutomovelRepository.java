package com.carrental.repository;

import com.carrental.model.Automovel;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface AutomovelRepository extends CrudRepository<Automovel, Long> {
    List<Automovel> findAllByIdIn(List<Long> ids);}