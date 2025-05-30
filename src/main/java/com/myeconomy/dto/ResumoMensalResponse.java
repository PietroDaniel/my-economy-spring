package com.myeconomy.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

import lombok.Data;

@Data
public class ResumoMensalResponse {
    private YearMonth mesReferencia;
    private BigDecimal totalDespesas;
    private BigDecimal limiteMensal;
    private boolean temLimite;
    private boolean economizou;
    private BigDecimal saldo;
    private String mensagem;
    
    public void calcularStatus() {
        this.temLimite = limiteMensal != null;
        if (temLimite) {
            this.saldo = limiteMensal.subtract(totalDespesas);
            this.economizou = saldo.compareTo(BigDecimal.ZERO) >= 0;
            
            if (economizou) {
                this.mensagem = "Parabéns! Você economizou R$ " + saldo + " este mês!";
            } else {
                this.mensagem = "Você ultrapassou seu limite em R$ " + saldo.abs() + 
                    ". No próximo mês você consegue!";
            }
        } else {
            this.mensagem = "Você ainda não definiu um limite para este mês.";
            this.economizou = false;
            this.saldo = BigDecimal.ZERO;
        }
    }
} 