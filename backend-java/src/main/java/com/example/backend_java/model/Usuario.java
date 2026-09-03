package com.example.backend_java.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

public class Usuario {
    @Entity
@Table(name = "usuarios")
public class Usuario {
@Id
@GeneratedValue(strategy = IDENTITY)
private Long id;
private String nome;
private String email;
private String senhaHash;
}
}
