package com.example.backend_java.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name="telemetrias")
public class Telemetria {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Column (nullable = false)
  private String evento;

  @Column (nullable = false)
  private LocalDateTime dataHora;

  @Column (nullable = false)
  private String mensagem;

  public Telemetria(){
  }

  public Long getId(){
    return id;
  }

  public void setId(Long id){
    this.id = id;
  }

  public String getEvento(){
    return evento;
  }

  public void setEvento(String evento){
    this.evento = evento;
  }

  public LocalDateTime getDataHora(){
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora){
    this.dataHora = dataHora;
  }

  public String getMensagem(){
    return mensagem;
  }

  public void setMensagem(String mensagem){
    this.mensagem = mensagem;
  }
}
