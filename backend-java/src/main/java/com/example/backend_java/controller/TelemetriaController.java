package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.TelemetriaDTO;
import com.example.backend_java.model.Telemetria;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetria")
public class TelemetriaController {

    @PostMapping
    public Telemetria criar(@Valid @RequestBody TelemetriaDTO  dto) {
        return null;
    }

    @GetMapping
    public List<Telemetria> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Telemetria atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TelemetriaDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
    