package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Consultor;

public interface ConsultorRepository extends JpaRepository<Consultor, Long> {

}