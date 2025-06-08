package com.safezone.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private LocalDeRisco local;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Enumerated(EnumType.STRING)
    private StatusAlerta status = StatusAlerta.PENDENTE;
}
