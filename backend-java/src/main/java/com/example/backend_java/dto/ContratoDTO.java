package com.example.backend_java.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.*;
@Getter
@Setter
public class ContratoDTO {
    
   @NotNull(message = "A data de início é obrigatória")
    private LocalDate data_inicio;

    @NotNull(message = "A data de fim é obrigatória")
    private LocalDate data_fim;

    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "O cliente é obrigatório")
    private Long cliente_id;

    @NotNull(message = "O serviço é obrigatório")
    private Long servico_id;

}
