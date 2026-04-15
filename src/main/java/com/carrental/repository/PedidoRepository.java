package com.carrental.repository;

import com.carrental.model.Pedido;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    @Query("""
    SELECT p
    FROM Pedido p
    WHERE p.cliente.id = :clienteId
    ORDER BY p.dataCriacao DESC
    """)
    List<Pedido> findByClienteId(Long clienteId);

    @Query("""
    SELECT COUNT(p)
    FROM Pedido p
    JOIN p.automoveis a
    WHERE a.id = :automovelId
      AND p.status IN ('PENDENTE', 'APROVADO')
      AND p.dataInicio <= :dataFim
      AND p.dataFim >= :dataInicio
""")
    long countAlugueisConflitantes(
            Long automovelId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    );
}