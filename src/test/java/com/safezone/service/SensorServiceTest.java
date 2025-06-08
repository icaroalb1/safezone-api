package com.safezone.service;

import com.safezone.dto.SensorDTO;
import com.safezone.exception.ResourceNotFoundException;
import com.safezone.model.Alerta;
import com.safezone.model.LocalDeRisco;
import com.safezone.model.Sensor;
import com.safezone.repository.AlertaRepository;
import com.safezone.repository.LocalDeRiscoRepository;
import com.safezone.repository.SensorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepo;

    @Mock
    private AlertaRepository alertaRepo;

    @Mock
    private LocalDeRiscoRepository localRepo;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvar_QuandoNivelNormal_DeveSalvarSemAlerta() {
        // Arrange
        SensorDTO dto = new SensorDTO();
        dto.setNivelAtual(5.0);
        dto.setLimiteNivel(10.0);
        dto.setLocalId(1L);

        LocalDeRisco local = new LocalDeRisco();
        local.setId(1L);

        when(localRepo.findById(1L)).thenReturn(Optional.of(local));
        when(sensorRepo.save(any(Sensor.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Sensor resultado = sensorService.salvar(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(5.0, resultado.getNivelAtual());
        assertEquals(10.0, resultado.getLimiteNivel());
        verify(alertaRepo, never()).save(any(Alerta.class));
    }

    @Test
    void salvar_QuandoNivelCritico_DeveCriarAlerta() {
        // Arrange
        SensorDTO dto = new SensorDTO();
        dto.setNivelAtual(15.0);
        dto.setLimiteNivel(10.0);
        dto.setLocalId(1L);

        LocalDeRisco local = new LocalDeRisco();
        local.setId(1L);

        when(localRepo.findById(1L)).thenReturn(Optional.of(local));
        when(sensorRepo.save(any(Sensor.class))).thenAnswer(i -> i.getArgument(0));
        when(alertaRepo.save(any(Alerta.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Sensor resultado = sensorService.salvar(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(15.0, resultado.getNivelAtual());
        assertEquals(10.0, resultado.getLimiteNivel());
        verify(alertaRepo, times(1)).save(any(Alerta.class));
    }

    @Test
    void listar_DeveRetornarPaginaDeSensores() {
        // Arrange
        String cidade = "São Paulo";
        String bairro = "Centro";
        Pageable pageable = PageRequest.of(0, 10);

        List<Sensor> sensores = Arrays.asList(
            new Sensor(), new Sensor()
        );
        Page<Sensor> page = new PageImpl<>(sensores);

        when(sensorRepo.findByLocal_CidadeContainingAndLocal_BairroContaining(
            cidade, bairro, pageable)).thenReturn(page);

        // Act
        Page<Sensor> resultado = sensorService.listar(cidade, bairro, pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
    }

    @Test
    void buscarPorId_QuandoSensorExiste_DeveRetornarSensor() {
        // Arrange
        Long id = 1L;
        Sensor sensor = new Sensor();
        sensor.setId(id);

        when(sensorRepo.findById(id)).thenReturn(Optional.of(sensor));

        // Act
        Sensor resultado = sensorService.buscarPorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    void buscarPorId_QuandoSensorNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 1L;
        when(sensorRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> sensorService.buscarPorId(id));
    }

    @Test
    void deletar_QuandoSensorExiste_DeveDeletar() {
        // Arrange
        Long id = 1L;
        when(sensorRepo.existsById(id)).thenReturn(true);

        // Act
        sensorService.deletar(id);

        // Assert
        verify(sensorRepo, times(1)).deleteById(id);
    }

    @Test
    void deletar_QuandoSensorNaoExiste_DeveLancarExcecao() {
        // Arrange
        Long id = 1L;
        when(sensorRepo.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> sensorService.deletar(id));
    }
} 