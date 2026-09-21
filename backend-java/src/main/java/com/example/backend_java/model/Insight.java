package com.example.backend_java.model;

import jakarta.persistence.*;

@Entity 
@Table(name = "insights")
public class Insight {

  @Id
  @GeneratedValue (strategy=GenerationType.AUTO)
  private Long id;
  
  @Column(nullable = false, length = 30)
  private String tipo;

  @Column(nullable = false, length = 200)
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;

  public Insight(){
  }

  public Long getId(){
    return id;
  }

  public void setId(Long id){
    this.id = id;
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

  public Cliente getCliente(){
    return cliente;
  }

  public void setCliente(Cliente cliente){
    this.cliente = cliente;
  }

}
