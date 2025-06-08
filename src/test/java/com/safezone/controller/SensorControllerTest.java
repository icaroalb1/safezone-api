package com.safezone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safezone.config.TestConfig;
import com.safezone.config.TestSecurityConfig;
import com.safezone.dto.SensorDTO;
import com.safezone.model.Sensor;
import com.safezone.service.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TestConfig.class, TestSecurityConfig.class})
public class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorService sensorService;

    @Test
    public void criar_DeveRetornarSensorCriado() throws Exception {
        SensorDTO dto = new SensorDTO();
        dto.setNivelAtual(5.0);
        dto.setLimiteNivel(10.0);
        dto.setLocalId(1L);

        Sensor sensor = new Sensor();
        sensor.setId(1L);
        sensor.setNivelAtual(dto.getNivelAtual());
        sensor.setLimiteNivel(dto.getLimiteNivel());

        when(sensorService.salvar(any(SensorDTO.class))).thenReturn(sensor);

        mockMvc.perform(post("/sensores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sensor.getId()))
                .andExpect(jsonPath("$.nivelAtual").value(sensor.getNivelAtual()))
                .andExpect(jsonPath("$.limiteNivel").value(sensor.getLimiteNivel()));
    }

    @Test
    public void listar_DeveRetornarPaginaDeSensores() throws Exception {
        Sensor sensor = new Sensor();
        sensor.setId(1L);
        sensor.setNivelAtual(5.0);
        sensor.setLimiteNivel(10.0);

        Page<Sensor> page = new PageImpl<>(List.of(sensor));

        when(sensorService.listar(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/sensores")
                .param("cidade", "São Paulo")
                .param("bairro", "Centro")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(sensor.getId()))
                .andExpect(jsonPath("$.content[0].nivelAtual").value(sensor.getNivelAtual()))
                .andExpect(jsonPath("$.content[0].limiteNivel").value(sensor.getLimiteNivel()));
    }

    @Test
    public void buscarPorId_QuandoSensorExiste_DeveRetornarSensor() throws Exception {
        Sensor sensor = new Sensor();
        sensor.setId(1L);
        sensor.setNivelAtual(5.0);
        sensor.setLimiteNivel(10.0);

        when(sensorService.buscarPorId(anyLong())).thenReturn(sensor);

        mockMvc.perform(get("/sensores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sensor.getId()))
                .andExpect(jsonPath("$.nivelAtual").value(sensor.getNivelAtual()))
                .andExpect(jsonPath("$.limiteNivel").value(sensor.getLimiteNivel()));
    }

    @Test
    public void deletar_QuandoSensorExiste_DeveRetornarNoContent() throws Exception {
        doNothing().when(sensorService).deletar(anyLong());

        mockMvc.perform(delete("/sensores/1"))
                .andExpect(status().isNoContent());
    }
} 