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
@Table(name = "servicos")

public class Servico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_servico;

  @Column(nullable = false, length = 50)
  private String nome;

  @Column(nullable = false, length = 40)
  private String categoria;

  @OneToMany (mappedBy = "servico")
  private List<Contrato> contratos;

  public Servico(){
  }

  public Long getId(){
    return id_servico;
  }

  public void setId(Long id_servico){
    this.id_servico = id_servico;
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
