package com.myeconomy.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @Column(unique = true)
    private String email;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String senha;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDate dataCriacao;
} 