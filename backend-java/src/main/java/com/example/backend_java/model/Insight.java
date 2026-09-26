package com.example.backend_java.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "insights")
public class Insight {

  @Id
  @GeneratedValue (strategy=GenerationType.AUTO)
  private Long id_insight;
  
  @Column(nullable = false, length = 30)
  private String tipo;

  @Column(nullable = false, length = 200)
  private String descricao;

  @Column (nullable = false)
  private LocalDate data_geracao;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "contrato_id")
  private Contrato contrato;

  public Insight(){
  }

  public Long getId(){
    return id_insight;
  }

  public void setId(Long id_insight){
    this.id_insight = id_insight;
  }

  public String getTipo(){
    return tipo;
  }

  public void setTipo(String tipo){
    this.tipo = tipo;
  }

  public String getDescricao(){
    return descricao;
  }

  public void setDescricao(String descricao){
    this.descricao = descricao;
  }

  public LocalDate getDataGeracao(){
    return data_geracao;
  }

  public void setDataGeracao(LocalDate data_geracao){
    this.data_geracao = data_geracao;
  }

  public Cliente getCliente(){
    return cliente;
  }

  public void setCliente(Cliente cliente){
    this.cliente = cliente;
  }

  public Contrato getContrato(){
    return contrato;
  }

  public void setContrato(Contrato contrato){
    this.contrato = contrato;
  }

}
