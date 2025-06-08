package com.safezone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double nivelAtual;

    @NotNull
    private Double limiteNivel;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocalDeRisco local;
}
