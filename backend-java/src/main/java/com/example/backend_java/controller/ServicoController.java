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
            @PathVariable Long id,
            @RequestBody ServicoDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
