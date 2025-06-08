package com.safezone.repository;

import com.safezone.model.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    Page<Alerta> findBySensorIdAndStatus(Long sensorId, String status, Pageable pageable);
    Page<Alerta> findBySensorId(Long sensorId, Pageable pageable);
    Page<Alerta> findByStatus(String status, Pageable pageable);
}
