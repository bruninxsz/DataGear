package com.example.backend_java.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.backend_java.model.Cliente;
import com.example.backend_java.model.Status;
import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.repository.ClienteRepository;
import com.example.backend_java.service.AnalyticsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final AnalyticsService analyticsService;

    public ClienteController(ClienteRepository clienteRepository, AnalyticsService analyticsService) {
        this.clienteRepository = clienteRepository;
        this.analyticsService = analyticsService;
    }

    @PostMapping("/importar")
    public ResponseEntity<Map<String, Object>> importar(@RequestBody List<Map<String, Object>> linhas) {
        return ResponseEntity.ok(analyticsService.processarImportacao(linhas));
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return analyticsService.obterDashboard();
    }

    @GetMapping("listar")
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Cliente> encerrarCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        cliente.setStatus(Status.ENCERRADO);
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return null;
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> removerTodos() {
        clienteRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
