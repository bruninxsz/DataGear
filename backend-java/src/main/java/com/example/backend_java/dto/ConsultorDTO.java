package com.example.backend_java.dto;

import jakarta.validation.constraints.*;

public class ConsultorDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    private String telefone;

    @NotBlank 
    private String senha;

    public String getNome(){
        return nome;
    }

    public void setNome(){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(){
        this.senha = senha;
    }
}