package com.example.backend_java.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.backend_java.dto.ServicoDTO;
import com.example.backend_java.model.Servico;
import com.example.backend_java.repository.ServicoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servico")
public class ServicoController {

    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoRepository servicoRepository){
        this.servicoRepository = servicoRepository;
    }

    @PostMapping
    public Servico criar(@Valid @RequestBody ServicoDTO  dto) {
        return null;
    }

    @GetMapping
    public List<Servico> listar() {
        return servicoRepository.findAll();
    }

    @PutMapping("/{id}")
    public Servico atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ServicoDTO dto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        // não precisa retornar nada
    }
}
