package com.carrental.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendimentos")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_inicio", nullable = false)
	private LocalDateTime dataInicio;

	@Column(name = "data_fim")
	private LocalDateTime dataFim;

	@Positive
	@Column(name = "valor_mensal", nullable = false, precision = 15, scale = 2)
	private BigDecimal valorMensal;

	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private Cliente cliente;

}