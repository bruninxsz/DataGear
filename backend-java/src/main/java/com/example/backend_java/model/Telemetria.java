package com.example.backend_java.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name="telemetrias")
public class Telemetria {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id_telemetria;

  @Column (nullable = false, length = 50)
  private String arquivo_nome; 

  @Column (nullable = false)
  private String tipo_evento;

  @Column (nullable = false)
  private LocalDateTime data_hora;

  @Column (nullable = false)
  private String mensagem_erro;

  @Column (nullable = false)
  private Status status_telemetria;

  @ManyToOne
  @JoinColumn(name = "consultor_id")
  private Consultor consultor;

  public Telemetria(){
  }

  public Long getId(){
    return id_telemetria;
  }

  public void setId(Long id_telemetria){
    this.id_telemetria = id_telemetria;
  }

  public String getArquivoNome(){
    return arquivo_nome;
  }

  public void setArquivoNome(String arquivo_nome){
    this.arquivo_nome = arquivo_nome;
  }

  public String getTipoEvento(){
    return tipo_evento;
  }

  public void setTipoEvento(String tipo_evento){
    this.tipo_evento = tipo_evento;
  }

  public LocalDateTime getDataHora(){
    return data_hora;
  }

  public void setDataHora(LocalDateTime data_hora){
    this.data_hora = data_hora;
  }

  public String getMensagemErro(){
    return mensagem_erro;
  }

  public Status getStatusTelemetria(){
    return status_telemetria;
  }

  public void setStatusTelemetria(Status status_telemetria){
    this.status_telemetria = status_telemetria;
  }

  public void setMensagemErro(String mensagem_erro){
    this.mensagem_erro = mensagem_erro;
  }

  public Consultor getConsultor(){
    return consultor;
  }

  public void setConsultor(Consultor consultor){
    this.consultor = consultor;
  }
}
