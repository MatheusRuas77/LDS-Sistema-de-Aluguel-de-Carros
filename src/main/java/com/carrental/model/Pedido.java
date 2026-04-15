package com.carrental.model;

import com.carrental.enums.StatusPedidoEnum;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "pedidos")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPedidoEnum status = StatusPedidoEnum.PENDENTE;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pedido_automoveis",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "automovel_id")
    )
    private List<Automovel> automoveis = new ArrayList<>();

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "valor_total", precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "agente_responsavel_id")
    private Agente agenteResponsavel;

    @JsonIgnore
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Contrato contrato;

    @JsonProperty("clienteNome")
    public String getClienteNome() {
        return cliente != null ? cliente.getNome() : null;
    }

    @JsonProperty("clienteId")
    public Long getClienteId() {
        return cliente != null ? cliente.getId() : null;
    }
}