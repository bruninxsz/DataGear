package com.example.backend_java.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
@Getter
@Setter
public class ConsultorDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    private String telefone;
}