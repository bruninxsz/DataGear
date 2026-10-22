
package com.example.backend_java.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.backend_java.model.nivelCliente;
import com.example.backend_java.model.Status;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    @NotBlank(message = "O código do cliente é obrigatório")
    @Size(max = 20, message = "O código deve ter no máximo 20 caracteres")
    private String codigo_cliente;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome_cliente;

    @NotBlank(message = "O consultor é obrigatório")
    @Size(max = 150, message = "O nome do consultor deve ter no máximo 150 caracteres")
    private String consultor;

    @NotBlank(message = "O segmento é obrigatório")
    @Size(max = 150, message = "O segmento deve ter no máximo 150 caracteres")
    private String segmento;

    @NotNull(message = "O nível do cliente é obrigatório")
    private nivelCliente nivel_cliente;

    @NotNull(message = "O status do cliente é obrigatório")
    private Status status;

    @NotNull(message = "O faturamento anual é obrigatório")
    @DecimalMin(value = "0.0", message = "O faturamento não pode ser negativo")
    private BigDecimal faturamento_anual;

    @NotBlank(message = "Os serviços contratados são obrigatórios")
    private String servicos_contratados;

    @NotNull(message = "A data de contratação é obrigatória")
    private LocalDate data_contratacao;

    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Pattern(regexp = "^[A-Za-z]{2}$",
             message = "A UF deve conter exatamente duas letras")
    private String uf;

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
}
