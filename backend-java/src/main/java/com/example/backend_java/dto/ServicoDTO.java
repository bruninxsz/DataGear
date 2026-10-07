package com.example.backend_java.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoDTO {

    @NotBlank(message = "O nome do serviço é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 40, message = "A categoria deve ter no máximo 40 caracteres")
    private String categoria;

    @Size(max = 150, message ="A descrição deveter no máximo 150 caracteres")
    private String descricao;
}