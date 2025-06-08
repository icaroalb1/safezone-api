package com.safezone.repository;

import com.safezone.model.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Page<Sensor> findByLocal_CidadeContainingAndLocal_BairroContaining(String cidade, String bairro, Pageable pageable);
}
