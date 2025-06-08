package com.safezone.service;

import com.safezone.dto.SensorDTO;
import com.safezone.exception.ResourceNotFoundException;
import com.safezone.model.Alerta;
import com.safezone.model.LocalDeRisco;
import com.safezone.model.Sensor;
import com.safezone.repository.AlertaRepository;
import com.safezone.repository.LocalDeRiscoRepository;
import com.safezone.repository.SensorRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {
    private final SensorRepository sensorRepo;
    private final AlertaRepository alertaRepo;
    private final LocalDeRiscoRepository localRepo;

    public SensorService(SensorRepository sensorRepo, AlertaRepository alertaRepo, LocalDeRiscoRepository localRepo) {
        this.sensorRepo = sensorRepo;
        this.alertaRepo = alertaRepo;
        this.localRepo = localRepo;
    }

    @Transactional
    @CacheEvict(value = {"sensores", "alertas"}, allEntries = true)
    public Sensor salvar(SensorDTO dto) {
        LocalDeRisco local = localRepo.findById(dto.getLocalId())
            .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado"));

        Sensor sensor = new Sensor();
        sensor.setNivelAtual(dto.getNivelAtual());
        sensor.setLimiteNivel(dto.getLimiteNivel());
        sensor.setLocal(local);

        if (sensor.getNivelAtual() > sensor.getLimiteNivel()) {
            Alerta alerta = new Alerta();
            alerta.setMensagem("ALERTA: nível crítico detectado!");
            alerta.setLocal(local);
            alertaRepo.save(alerta);
        }
        
        return sensorRepo.save(sensor);
    }

    @Cacheable(value = "sensores")
    public Page<Sensor> listar(String cidade, String bairro, Pageable pageable) {
        return sensorRepo.findByLocal_CidadeContainingAndLocal_BairroContaining(cidade, bairro, pageable);
    }

    @Cacheable(value = "sensores", key = "#id")
    public Sensor buscarPorId(Long id) {
        return sensorRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado"));
    }

    @Transactional
    @CacheEvict(value = {"sensores", "alertas"}, allEntries = true)
    public void deletar(Long id) {
        if (!sensorRepo.existsById(id)) {
            throw new ResourceNotFoundException("Sensor não encontrado");
        }
        sensorRepo.deleteById(id);
    }
}
