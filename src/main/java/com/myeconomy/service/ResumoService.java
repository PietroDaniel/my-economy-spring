package com.myeconomy.service;

import com.myeconomy.dto.ResumoMensalResponse;
import com.myeconomy.model.Usuario;
import com.myeconomy.repository.DespesaRepository;
import com.myeconomy.repository.LimiteMensalRepository;
import com.myeconomy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
public class ResumoService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private LimiteMensalRepository limiteMensalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public ResumoMensalResponse gerarResumoMensal(YearMonth mesReferencia) {
        Usuario usuario = getUsuarioAutenticado();
        
        ResumoMensalResponse resumo = new ResumoMensalResponse();
        resumo.setMesReferencia(mesReferencia);
        
        // Busca o total de despesas
        BigDecimal totalDespesas = despesaRepository.calcularTotalDespesasPorMes(usuario, mesReferencia);
        resumo.setTotalDespesas(totalDespesas != null ? totalDespesas : BigDecimal.ZERO);
        
        // Busca o limite mensal
        limiteMensalRepository.findByUsuarioAndMesReferencia(usuario, mesReferencia)
            .ifPresent(limite -> resumo.setLimiteMensal(limite.getValor()));
        
        // Calcula o status (economizou ou não)
        resumo.calcularStatus();
        
        return resumo;
    }

    private Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
} 