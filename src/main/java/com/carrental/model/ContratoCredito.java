package com.carrental.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "contratos_credito")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @OneToOne
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = false)
    private Agente banco;
}