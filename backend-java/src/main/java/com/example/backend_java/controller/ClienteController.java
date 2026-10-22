package com.example.backend_java.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.model.Cliente;
import com.example.backend_java.repository.ClienteRepository;
import com.example.backend_java.service.ClienteService;
import com.example.backend_java.model.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;

    public ClienteController(ClienteRepository clienteRepository, ClienteService clienteService) {
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }

    @PostMapping("/importar")
    public ResponseEntity<?> importar(
            @RequestBody List<@Valid ClienteDTO> dtos) {
        try {
            List<Cliente> clientes = clienteService.importar(dtos);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of(
                            "message", "Importação realizada com sucesso.",
                            "totalImportado", clientes.size()
                    )
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", e.getMessage())
            );
        }
    }

    @GetMapping("listar")
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Cliente> encerrarCliente(@PathVariable Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado"
                ));

        cliente.setStatus(Status.ENCERRADO);

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return ResponseEntity.ok(clienteAtualizado);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO dto) {
        return null;
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> removerTodos() {
        clienteRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
