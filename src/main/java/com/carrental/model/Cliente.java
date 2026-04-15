package com.carrental.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLIENTE")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @Column(length = 11, unique = true)
    private String cpf;

    @Column(length = 9, unique = true)
    private String rg;

    @Column(length = 100)
    private String profissao;
    @Valid
    @Size(max = 3, message = "A user can have at most 3 jobs")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rendimento> rendimentos = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();
}