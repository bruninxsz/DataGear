package com.example.backend_java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultores")
public class Consultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)

    private String nome;
    private String email;
    private String segmento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

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

    public Long getUsuario(){
        return usuario;
    }

    public void setUsuario(){
        this.usuario = usuario;
    }

}
