package com.safezone.controller;

import com.safezone.dto.LocalDeRiscoDTO;
import com.safezone.model.LocalDeRisco;
import com.safezone.service.LocalDeRiscoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locais")
@Tag(name = "Local de Risco Controller", description = "Endpoints para gerenciamento de locais de risco")
public class LocalDeRiscoController {

    @Autowired
    private LocalDeRiscoService localDeRiscoService;

    @GetMapping
    @Operation(summary = "Listar locais de risco", description = "Retorna uma página de locais de risco com filtros opcionais")
    public ResponseEntity<Page<LocalDeRisco>> listar(
            @Parameter(description = "Cidade do local") @RequestParam(required = false) String cidade,
            @Parameter(description = "Bairro do local") @RequestParam(required = false) String bairro,
            Pageable pageable) {
        return ResponseEntity.ok(localDeRiscoService.listar(cidade, bairro, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar local por ID", description = "Retorna um local de risco específico pelo ID")
    public ResponseEntity<LocalDeRisco> buscarPorId(
            @Parameter(description = "ID do local") @PathVariable Long id) {
        return ResponseEntity.ok(localDeRiscoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar local de risco", description = "Cria um novo local de risco")
    public ResponseEntity<LocalDeRisco> criar(
            @Parameter(description = "Dados do local de risco") @RequestBody LocalDeRiscoDTO localDeRiscoDTO) {
        return ResponseEntity.ok(localDeRiscoService.salvar(localDeRiscoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar local de risco", description = "Atualiza um local de risco existente")
    public ResponseEntity<LocalDeRisco> atualizar(
            @Parameter(description = "ID do local") @PathVariable Long id,
            @Parameter(description = "Dados atualizados do local") @RequestBody LocalDeRiscoDTO localDeRiscoDTO) {
        return ResponseEntity.ok(localDeRiscoService.atualizar(id, localDeRiscoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar local de risco", description = "Remove um local de risco pelo ID")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do local") @PathVariable Long id) {
        localDeRiscoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
} 