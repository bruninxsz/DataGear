package com.example.backend_java.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "consultor")
public class Consultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_consultor;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 15)
    private String telefone;

    @Column(nullable = false, length = 255)
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "consultor")
    private List<Telemetria> telemetrias;

    public Consultor() {
    }

    public Long getId() {
        return id_consultor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Telemetria> getTelemetrias() {
        return telemetrias;
    }

    public void setTelemetrias(List<Telemetria> telemetrias) {
        this.telemetrias = telemetrias;
    }
}
