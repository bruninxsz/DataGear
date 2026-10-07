package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.TelemetriaDTO;
import com.example.backend_java.model.Telemetria;
import com.example.backend_java.repository.TelemetriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetria")
public class TelemetriaController {

    private final TelemetriaRepository telemetriaRepository;
    
    public TelemetriaController(TelemetriaRepository telemetriaRepository){
        this.telemetriaRepository = telemetriaRepository;
    }

    @PostMapping
    public Telemetria criar(@Valid @RequestBody TelemetriaDTO  dto) {
        return null;
    }

    @GetMapping
    public List<Telemetria> listar() {
        return telemetriaRepository.findAll();
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
    