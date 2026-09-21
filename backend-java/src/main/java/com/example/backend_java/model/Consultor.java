package com.example.backend_java.model;

import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name = "consultores")
public class Consultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 50)
    private String segmento;

    @OneToMany (mappedBy = "consultor")
    private List<Cliente> clientes;
    
    public Consultor(){ 
    }

    public Long getId(){
        return id;
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

    public String getSegmento(){
        return segmento;
    }

    public void setSegmento(String segmento){
        this.segmento = segmento;
    }

    public List<Cliente> getClientes(){
      return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
