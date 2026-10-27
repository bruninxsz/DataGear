package com.example.backend_java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend_java.dto.ClienteDTO;
import com.example.backend_java.model.Cliente;
import com.example.backend_java.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public List<Cliente> importar(List<ClienteDTO> dtos) {

        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException(
                "A lista de clientes está vazia."
            );
        }

        // Validar toda a lista antes de salvar
        List<String> erros = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            ClienteDTO dto = dtos.get(i);
            int linha = i + 2;

            if (dto == null) {
                erros.add("Linha " + linha + ": registro vazio.");
                continue;
            }

            if (dto.getCodigo_cliente() == null
                    || dto.getCodigo_cliente().isBlank()) {
                erros.add("Linha " + linha
                    + ": código do cliente obrigatório.");
            }

            if (dto.getNome_cliente() == null
                    || dto.getNome_cliente().isBlank()) {
                erros.add("Linha " + linha
                    + ": nome do cliente obrigatório.");
            }

            if (dto.getConsultor() == null
                    || dto.getConsultor().isBlank()) {
                erros.add("Linha " + linha
                    + ": consultor obrigatório.");
            }

            if (dto.getSegmento() == null
                    || dto.getSegmento().isBlank()) {
                erros.add("Linha " + linha
                    + ": segmento obrigatório.");
            }

            if (dto.getNivel_cliente() == null) {
                erros.add("Linha " + linha
                    + ": nível do cliente obrigatório.");
            }

            // CORREÇÃO 1: Validação de obrigatoriedade do status
            if (dto.getStatus() == null) {
                erros.add("Linha " + linha
                    + ": status do cliente obrigatório.");
            }

            if (dto.getFaturamento_anual() == null
                    || dto.getFaturamento_anual().signum() < 0) {
                erros.add("Linha " + linha
                    + ": faturamento anual inválido.");
            }

            if (dto.getServicos_contratados() == null
                    || dto.getServicos_contratados().isBlank()) {
                erros.add("Linha " + linha
                    + ": serviços contratados obrigatórios.");
            }

            if (dto.getData_contratacao() == null) {
                erros.add("Linha " + linha
                    + ": data de contratação obrigatória.");
            }

            if (dto.getUf() != null && !dto.getUf().isBlank()
                    && !dto.getUf().matches("[A-Za-z]{2}")) {
                erros.add("Linha " + linha
                    + ": UF deve conter exatamente duas letras.");
            }
        }

        // Não salvar nenhum cliente se houver erros
        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(
                String.join("\n", erros)
            );
        }

        // Converter os DTOs para entidades
        List<Cliente> clientes = dtos.stream()
            .map(dto -> {
                Cliente cliente = new Cliente();

                cliente.setCodigo_cliente(dto.getCodigo_cliente());
                cliente.setNome_cliente(dto.getNome_cliente());
                cliente.setConsultor(dto.getConsultor());
                cliente.setSegmento(dto.getSegmento());
                cliente.setNivel_cliente(dto.getNivel_cliente());
                
                // CORREÇÃO 2: Atribuição do status para a entidade JPA
                cliente.setStatus(dto.getStatus());

                cliente.setFaturamento_anual(
                    dto.getFaturamento_anual()
                );
                cliente.setServicos_contratados(
                    dto.getServicos_contratados()
                );
                cliente.setData_contratacao(
                    dto.getData_contratacao()
                );
                cliente.setCidade(dto.getCidade());
                cliente.setUf(
                    dto.getUf() == null
                        ? null
                        : dto.getUf().toUpperCase()
                );

                return cliente;
            })
            .toList();

        // Salvar em uma única transação
        return clienteRepository.saveAll(clientes);
    }
}