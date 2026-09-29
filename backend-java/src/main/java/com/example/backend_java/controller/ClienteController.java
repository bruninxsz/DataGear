package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.model.Cliente;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @PostMapping
    public Cliente criar(@Valid @RequestBody ClienteDTO dto) {
        return null;
    }

    @GetMapping
    public List<Cliente> listar() {
        return null;
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @Valid 
            @PathVariable Long id,
            @RequestBody ClienteDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
