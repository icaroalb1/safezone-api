package com.safezone.controller;

import com.safezone.model.Alerta;
import com.safezone.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
@Tag(name = "Alerta Controller", description = "Endpoints para gerenciamento de alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    @Operation(summary = "Listar alertas", description = "Retorna uma página de alertas com filtros opcionais")
    public ResponseEntity<Page<Alerta>> listar(
            @Parameter(description = "ID do sensor") @RequestParam(required = false) Long sensorId,
            @Parameter(description = "Status do alerta") @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity.ok(alertaService.listar(sensorId, status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar alerta por ID", description = "Retorna um alerta específico pelo ID")
    public ResponseEntity<Alerta> buscarPorId(
            @Parameter(description = "ID do alerta") @PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualizar status do alerta", description = "Atualiza o status de um alerta existente")
    public ResponseEntity<Alerta> atualizarStatus(
            @Parameter(description = "ID do alerta") @PathVariable Long id,
            @Parameter(description = "Novo status do alerta") @RequestParam String status) {
        return ResponseEntity.ok(alertaService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar alerta", description = "Remove um alerta pelo ID")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do alerta") @PathVariable Long id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 