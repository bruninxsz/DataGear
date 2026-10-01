package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Telemetria;

public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {

};