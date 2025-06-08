package com.safezone.controller;

import com.safezone.dto.LoginRequest;
import com.safezone.model.Usuario;
import com.safezone.service.UsuarioService;
import com.safezone.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.registrar(usuario);
        return ResponseEntity.ok(novo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(request.getEmail());

        if (usuarioOpt.isEmpty())
            return ResponseEntity.status(401).body("Usuário não encontrado");

        Usuario usuario = usuarioOpt.get();

        if (!usuarioService.senhaCorreta(request.getSenha(), usuario.getSenha()))
            return ResponseEntity.status(401).body("Senha inválida");

        String token = jwtUtil.gerarToken(usuario.getEmail());

        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
}
