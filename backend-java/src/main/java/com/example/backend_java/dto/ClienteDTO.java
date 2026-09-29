package com.example.backend_java.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.example.backend_java.model.nivelCliente;

import jakarta.validation.constraints.*;
@Getter 
@Setter

public class ClienteDTO {

    @NotBlank(message = "O nome da empresa é obrigatório")
    @Size(max = 150)
    private String nome_empresa;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "O segmento é obrigatório")
    @Size(max = 150)
    private String segmento;

    @NotNull(message = "O faturamento anual é obrigatório")
    private BigDecimal faturamento_anual;

    @NotNull(message = "O nível do cliente é obrigatório")
    private nivelCliente nivel_cliente;

  
}
