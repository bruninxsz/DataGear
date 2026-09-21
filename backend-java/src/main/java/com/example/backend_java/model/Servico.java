package com.example.backend_java.model;

import jakarta.persistence.*;
import java.util.List;

@Entity 
@Table(name = "servicos")

public class Servico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String nome;

  @Column(nullable = false, length = 40)
  private String categoria;

  @OneToMany (mappedBy = "servico")
  private List<Contrato> contratos;

  public Servico(){
  }

  public Long getId(){
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }
  
  public String getNome(){
    return nome;
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public String getCategoria(){
    return categoria;
  }

  public void setCategoria(String categoria){
    this.categoria = categoria;
  }

  public List<Contrato> getContratos(){
    return contratos;
  }

  public void setContratos(List<Contrato> contratos){
    this.contratos = contratos;
  }
}
