package com.example.backend_java.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsightDTO {

    @NotBlank(message = "O tipo é obrigatório")
    @Size(max = 30, message = "O tipo deve ter no máximo 30 caracteres")
    private String tipo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres")
    private String descricao;

    @NotNull(message = "O cliente é obrigatório")
    private Long cliente_id;

    @NotNull(message = "O contrato é obrigatório")
    private Long contrato_id;
}