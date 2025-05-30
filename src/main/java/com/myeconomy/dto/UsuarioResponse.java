package com.myeconomy.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class UsuarioResponse {
    private UUID id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
    private LocalDate dataCriacao;
} 