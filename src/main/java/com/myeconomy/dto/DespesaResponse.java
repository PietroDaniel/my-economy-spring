package com.myeconomy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import lombok.Data;

@Data
public class DespesaResponse {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private YearMonth mesReferencia;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
} 