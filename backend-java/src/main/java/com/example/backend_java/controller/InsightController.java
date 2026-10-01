package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.InsightDTO;
import com.example.backend_java.model.Insight;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/insight")
public class InsightController {

    @PostMapping
    public Insight criar(@Valid @RequestBody InsightDTO  dto) {
        return null;
    }

    @GetMapping
    public List<Insight> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Insight atualizar(
            @PathVariable Long id,
            @Valid @RequestBody InsightDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
