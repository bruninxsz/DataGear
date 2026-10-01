package com.example.backend_java.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelemetriaDTO {

    @NotBlank(message = "O nome do arquivo é obrigatório")
    @Size(max = 50, message = "O nome do arquivo deve ter no máximo 50 caracteres")
    private String arquivo_nome;

    @NotBlank(message = "O tipo do evento é obrigatório")
    private String tipo_evento;

    @NotBlank(message = "A mensagem de erro é obrigatória")
    private String mensagem_erro;

    private Long consultor_id;
}