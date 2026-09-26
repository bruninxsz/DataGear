package com.example.backend_java.model;

import java.math.BigDecimal;
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
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_contrato;

    @Column(nullable = false)
    private LocalDate data_inicio;
    
    @Column(nullable = false)
    private LocalDate data_fim;

    @Column(nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne 
    @JoinColumn (name = "servico_id")
    private Servico servico;

    public Contrato() {
    }

    public Long getId() {
        return id_contrato;
    }

    public void setId(Long id) {
        this.id_contrato = id;
    }

    public LocalDate getDataInicio() {
        return data_inicio;
    }

    public void setDataInicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getDataFim() {
        return data_fim;
    }

    public void setDataFim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico(){
        return servico;
    }

    public void setServico(Servico servico){
        this.servico = servico;
    }
}
