package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Consultor;
import java.util.Optional;

public interface ConsultorRepository extends JpaRepository<Consultor, Long> {

    Optional<Consultor> findByEmail(String email);

}
