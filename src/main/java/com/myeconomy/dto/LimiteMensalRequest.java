package com.myeconomy.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LimiteMensalRequest {
    
    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "O mês de referência é obrigatório")
    private YearMonth mesReferencia;
} 