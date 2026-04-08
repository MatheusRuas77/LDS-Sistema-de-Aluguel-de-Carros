package com.carrental.repository;

import com.carrental.model.Agente;
import com.carrental.enums.AgenteRoleEnum;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface AgenteRepository extends CrudRepository<Agente, Long> {
    List<Agente> findByRole(AgenteRoleEnum role);
}