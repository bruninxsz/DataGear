        package com.example.backend_java.model;

        import java.util.List;

        import jakarta.persistence.*;
import tools.jackson.databind.annotation.EnumNaming;
        @Entity
        @Table(name = "cliente")
        public class Cliente {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id_cliente;

            @Column(nullable = false, length = 150)
            private String nome_empresa;

            @Column(nullable = false, length = 150)
            private String segmento;

            @Column(nullable = false, length = 150)
            private double faturamento_anual;   

            @Enumerated(EnumType.STRING)
            @Column(nullable = false, length = 1)
            private nivelCliente nivel;
e
            @Enumerated(EnumType.STRING)
            @Column(nullable = false)
            private Status status;

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

            public String getSegmento() {
                return segmento;
            }

            public void setSegmento(String segmento) {
                this.segmento = segmento;
            }

            public Status getStatusCliente() {
                return status;
            }

            public void setStatusCliente(Status status) {
                this.status = status;
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
                return nivel;
            }

            public void setNivelCliente(nivelCliente nivel) {
                this.nivel = nivel;
            }

        }
