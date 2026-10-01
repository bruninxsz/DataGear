package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ContratoDTO;
import com.example.backend_java.model.Contrato;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contrato")
public class ContratoController {

    @PostMapping
    public Contrato criar(@Valid @RequestBody ContratoDTO dto) {
        return null;
    }

    @GetMapping
    public List<Contrato> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Contrato atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ContratoDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
