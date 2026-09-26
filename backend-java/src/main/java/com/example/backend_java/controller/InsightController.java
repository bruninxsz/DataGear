package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestBody InsightDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
