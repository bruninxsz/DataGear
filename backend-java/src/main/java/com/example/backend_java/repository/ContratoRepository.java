package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Contrato;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

}