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

import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.model.Cliente;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consultor")
public class ConsultorController {

    @PostMapping
    public Consultor criar(@Valid @RequestBody ConsultorDTO dto) {
        return null;
    }

    @GetMapping
    public List<Cliente> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @PathVariable Long id,
            @RequestBody ClienteDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
