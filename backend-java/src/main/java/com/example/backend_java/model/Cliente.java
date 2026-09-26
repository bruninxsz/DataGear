package com.example.backend_java.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    @Column(nullable = false, length = 150)
    private String nome_empresa;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 150)
    private String segmento;

    @Column(nullable = false, length = 150)
    private double faturamento_anual;

    @Column(nullable = false, length = 1)
    private nivelCliente nivel_cliente;

    @Column(nullable = false)
    private Status status_cliente;

    @ManyToOne
    @JoinColumn(name = "consultor_id")
    private Consultor consultor;

    @OneToMany(mappedBy = "cliente")
    private List<Contrato> contratos;

    @OneToMany(mappedBy = "cliente")
    private List<Insight> insights;

    public Cliente() {
    }

    public Long getId() {
        return id_cliente;
    }

    public void setId(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNomeEmpresa() {
        return nome_empresa;
    }

    public void setNomeEmpresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public Status getStatusCliente() {
        return status_cliente;
    }

    public void setStatusCliente(Status status_cliente) {
        this.status_cliente = status_cliente;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public Consultor getConsultor(){
        return consultor;
    }

    public void setConsultor(Consultor consultor){
        this.consultor = consultor;
    }

    public List<Insight> getInsights(){
        return insights;
    }

    public void setInsight(List<Insight> insights){
        this.insights = insights;
    }

    public double getFaturamentoAnual() {
        return faturamento_anual;
    }

    public void setFaturamentoAnual(double faturamento_anual) {
        this.faturamento_anual = faturamento_anual;
    }

    public nivelCliente getNivelCliente() {
        return nivel_cliente;
    }

    public void setNivelCliente(nivelCliente nivel_cliente) {
        this.nivel_cliente = nivel_cliente;
    }

}
