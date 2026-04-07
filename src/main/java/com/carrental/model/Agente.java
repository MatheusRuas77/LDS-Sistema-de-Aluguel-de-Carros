package com.carrental.model;

import com.carrental.enums.AgenteRoleEnum;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agentes")
@DiscriminatorValue("AGENTE")
@Serdeable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agente extends Usuario {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private AgenteRoleEnum role;

}