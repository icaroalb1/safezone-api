package com.safezone.repository;

import com.safezone.model.LocalDeRisco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalDeRiscoRepository extends JpaRepository<LocalDeRisco, Long> {
    Page<LocalDeRisco> findByCidadeAndBairro(String cidade, String bairro, Pageable pageable);
    Page<LocalDeRisco> findByCidade(String cidade, Pageable pageable);
    Page<LocalDeRisco> findByBairro(String bairro, Pageable pageable);
}
