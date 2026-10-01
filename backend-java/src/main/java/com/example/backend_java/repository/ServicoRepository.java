package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}