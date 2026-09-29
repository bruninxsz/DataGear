package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ServicoDTO;
import com.example.backend_java.model.Servico;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servico")
public class ServicoController {

    @PostMapping
    public Servico criar(@Valid @RequestBody ServicoDTO  dto) {
        return null;
    }

    @GetMapping
    public List<Servico> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Servico atualizar(
            @Valid 
            @PathVariable Long id,
            @RequestBody ServicoDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
