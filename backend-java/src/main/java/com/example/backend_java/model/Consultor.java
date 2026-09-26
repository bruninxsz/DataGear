package com.example.backend_java.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "consultores")
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
    private Long senhaHash;

    @OneToMany (mappedBy = "consultor")
    private List<Cliente> clientes;
    
    @OneToMany (mappedBy = "consultor")
    private List<Telemetria> telemetrias;

    public Consultor(){ 
    }

    public Long getId(){
        return id_consultor;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
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

    public Long getSenhaHash(){
        return senhaHash;
    }

    public void setSenhaHash(Long senhaHash){
        this.senhaHash = senhaHash;
    }

    public List<Cliente> getClientes(){
      return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Telemetria> getTelemetrias(){
      return telemetrias;
    }

    public void setTelemetrias(List<Telemetria> telemetrias) {
        this.telemetrias = telemetrias;
    }
}
