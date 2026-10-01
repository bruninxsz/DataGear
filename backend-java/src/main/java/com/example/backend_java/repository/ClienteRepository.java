package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}