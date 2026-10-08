package com.example.backend_java.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.LoginDTO;
import com.example.backend_java.model.Consultor;
import com.example.backend_java.repository.ConsultorRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ConsultorRepository consultorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            ConsultorRepository consultorRepository,
            PasswordEncoder passwordEncoder) {

        this.consultorRepository = consultorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        Consultor consultor = consultorRepository
                .findByEmail(dto.getEmail())
                .orElse(null);

        if (consultor == null) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("mensagem", "E-mail ou senha inválidos"));
        }

        if (!passwordEncoder.matches(dto.getSenha(), consultor.getSenha())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("mensagem", "E-mail ou senha inválidos"));
        }

        return ResponseEntity.ok(
                Map.of(
                    "mensagem", "Login realizado com sucesso",
                    "id", consultor.getId(),
                    "nome", consultor.getNome(),
                    "email", consultor.getEmail()
                )
        );
    }
}