package com.safezone.service;

import com.safezone.dto.LocalDeRiscoDTO;
import com.safezone.model.LocalDeRisco;
import com.safezone.repository.LocalDeRiscoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalDeRiscoService {

    @Autowired
    private LocalDeRiscoRepository repository;

    @Transactional(readOnly = true)
    public Page<LocalDeRisco> listar(String cidade, String bairro, Pageable pageable) {
        if (cidade != null && bairro != null) {
            return repository.findByCidadeAndBairro(cidade, bairro, pageable);
        } else if (cidade != null) {
            return repository.findByCidade(cidade, pageable);
        } else if (bairro != null) {
            return repository.findByBairro(bairro, pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional
    public LocalDeRisco salvar(LocalDeRiscoDTO dto) {
        LocalDeRisco local = new LocalDeRisco();
        local.setCidade(dto.getCidade());
        local.setBairro(dto.getBairro());
        local.setRua(dto.getRua());
        local.setNumero(dto.getNumero());
        return repository.save(local);
    }

    @Transactional(readOnly = true)
    public LocalDeRisco buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local de risco não encontrado"));
    }

    @Transactional
    public LocalDeRisco atualizar(Long id, LocalDeRiscoDTO dto) {
        LocalDeRisco local = buscarPorId(id);
        local.setCidade(dto.getCidade());
        local.setBairro(dto.getBairro());
        local.setRua(dto.getRua());
        local.setNumero(dto.getNumero());
        return repository.save(local);
    }

    @Transactional
    public void deletar(Long id) {
        repository.deleteById(id);
    }
} 