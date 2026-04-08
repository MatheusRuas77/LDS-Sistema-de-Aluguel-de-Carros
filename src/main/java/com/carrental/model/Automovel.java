package com.carrental.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "automoveis")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 20, unique = true)
    private String matricula;

    @Positive
    @Column(nullable = false)
    private int ano;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotBlank
    @Column(nullable = false, length = 10, unique = true)
    private String placa;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String marca;

    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Usuario proprietario;

}