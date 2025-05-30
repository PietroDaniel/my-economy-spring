package com.myeconomy.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String tipo;
    private UUID id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
} 