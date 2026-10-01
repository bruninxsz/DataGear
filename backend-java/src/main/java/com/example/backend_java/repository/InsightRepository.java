package com.example.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend_java.model.Insight;

public interface InsightRepository extends JpaRepository<Insight, Long> {

}