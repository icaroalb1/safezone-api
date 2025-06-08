package com.safezone.service;

import com.safezone.model.Alerta;
import com.safezone.model.StatusAlerta;
import com.safezone.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    @Transactional(readOnly = true)
    public Page<Alerta> listar(Long sensorId, String status, Pageable pageable) {
        if (sensorId != null && status != null) {
            return repository.findBySensorIdAndStatus(sensorId, status, pageable);
        } else if (sensorId != null) {
            return repository.findBySensorId(sensorId, pageable);
        } else if (status != null) {
            return repository.findByStatus(status, pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Alerta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
    }

    @Transactional
    public Alerta atualizarStatus(Long id, String status) {
        Alerta alerta = buscarPorId(id);
        alerta.setStatus(StatusAlerta.valueOf(status.toUpperCase()));
        return repository.save(alerta);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
} 