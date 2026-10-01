package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.model.Cliente;
import com.example.backend_java.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @PostMapping
    public Cliente criar(@Valid @RequestBody ClienteDTO dto) {
        return null;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
