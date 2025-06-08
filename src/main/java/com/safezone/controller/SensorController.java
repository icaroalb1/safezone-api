package com.safezone.controller;

import com.safezone.dto.SensorDTO;
import com.safezone.model.Sensor;
import com.safezone.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensores")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PostMapping
    public ResponseEntity<Sensor> criar(@RequestBody @Valid SensorDTO sensorDTO) {
        return ResponseEntity.ok(sensorService.salvar(sensorDTO));
    }

    @GetMapping
    public ResponseEntity<Page<Sensor>> listar(
            @RequestParam(defaultValue = "") String cidade,
            @RequestParam(defaultValue = "") String bairro,
            Pageable pageable) {
        return ResponseEntity.ok(sensorService.listar(cidade, bairro, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
