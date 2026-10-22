
package com.example.backend_java.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id_cliente;

    @Column(name = "codigo_cliente", nullable = false,
            unique = true, length = 20)
    private String codigo_cliente;

    @Column(name = "nome_cliente", nullable = false, length = 150)
    private String nome_cliente;

    @Column(name = "consultor", nullable = false, length = 150)
    private String consultor;

    @Column(name = "segmento", nullable = false, length = 150)
    private String segmento;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_cliente", nullable = false, length = 1)
    private nivelCliente nivel_cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private Status status;

    @Column(name = "faturamento_anual", nullable = false,
            precision = 15, scale = 2)
    private BigDecimal faturamento_anual;

    @Column(name = "servicos_contratados", nullable = false,
            columnDefinition = "TEXT")
    private String servicos_contratados;

    @Column(name = "data_contratacao", nullable = false)
    private LocalDate data_contratacao;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @OneToMany(mappedBy = "cliente")
    private List<Contrato> contratos;

    @OneToMany(mappedBy = "cliente")
    private List<Insight> insights;

    public Cliente() {
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getConsultor() {
        return consultor;
    }

    public void setConsultor(String consultor) {
        this.consultor = consultor;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public nivelCliente getNivel_cliente() {
        return nivel_cliente;
    }

    public void setNivel_cliente(nivelCliente nivel_cliente) {
        this.nivel_cliente = nivel_cliente;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public BigDecimal getFaturamento_anual() {
        return faturamento_anual;
    }

    public void setFaturamento_anual(BigDecimal faturamento_anual) {
        this.faturamento_anual = faturamento_anual;
    }

    public String getServicos_contratados() {
        return servicos_contratados;
    }

    public void setServicos_contratados(String servicos_contratados) {
        this.servicos_contratados = servicos_contratados;
    }

    public LocalDate getData_contratacao() {
        return data_contratacao;
    }

    public void setData_contratacao(LocalDate data_contratacao) {
        this.data_contratacao = data_contratacao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<Insight> getInsights() {
        return insights;
    }

    public void setInsights(List<Insight> insights) {
        this.insights = insights;
    }
}
