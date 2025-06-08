package com.safezone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocalDeRiscoDTO {
    private Long id;
    
    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;
    
    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A rua é obrigatória")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    private String numero;
} 