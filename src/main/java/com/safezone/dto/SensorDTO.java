package com.safezone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SensorDTO {
    private Long id;
    
    @NotNull(message = "O nível atual é obrigatório")
    private Double nivelAtual;
    
    @NotNull(message = "O limite de nível é obrigatório")
    private Double limiteNivel;
    
    @NotNull(message = "O local é obrigatório")
    private Long localId;
} 