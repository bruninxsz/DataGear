package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ConsultorDTO;
import com.example.backend_java.model.Consultor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consultor")
public class UsuarioController {

    @PostMapping
    public Consultor criar(@Valid @RequestBody ConsultorDTO dto) {
        return null;
    }

    @GetMapping
    public List<Consultor> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Consultor atualizar(
            @PathVariable Long id,
            @RequestBody ConsultorDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
