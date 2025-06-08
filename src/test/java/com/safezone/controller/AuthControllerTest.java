package com.safezone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safezone.config.TestConfig;
import com.safezone.config.TestSecurityConfig;
import com.safezone.dto.LoginRequest;
import com.safezone.model.Usuario;
import com.safezone.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({TestConfig.class, TestSecurityConfig.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void register_DeveRetornarUsuarioCriado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("senha123");

        when(usuarioService.registrar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(usuario.getEmail()));
    }

    @Test
    public void login_QuandoCredenciaisValidas_DeveRetornarToken() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG"); // senha123

        LoginRequest request = new LoginRequest("teste@teste.com", "senha123");

        when(usuarioService.buscarPorEmail(request.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioService.senhaCorreta(request.getSenha(), usuario.getSenha())).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void login_QuandoUsuarioNaoExiste_DeveRetornarErro() throws Exception {
        LoginRequest request = new LoginRequest("teste@teste.com", "senha123");

        when(usuarioService.buscarPorEmail(request.getEmail())).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Usuário não encontrado"));
    }

    @Test
    public void login_QuandoSenhaInvalida_DeveRetornarErro() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@teste.com");
        usuario.setSenha("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG"); // senha123

        LoginRequest request = new LoginRequest("teste@teste.com", "senhaErrada");

        when(usuarioService.buscarPorEmail(request.getEmail())).thenReturn(Optional.of(usuario));
        when(usuarioService.senhaCorreta(request.getSenha(), usuario.getSenha())).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Senha inválida"));
    }
} 