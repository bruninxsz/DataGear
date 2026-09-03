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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @PostMapping
    public Usuario criar(
            @Valid @RequestBody UsuarioDTO dto) {
        // ...
    }

    @GetMapping
    public List<Usuario> listar() {
        // ...
    }

    @PutMapping("/{id}")
    public Usuario atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioDTO dto) {
        // ...
    }

    @DeleteMapping("/{id}")
    public void remover(
            @PathVariable Long id) {
        // ...
    }
}