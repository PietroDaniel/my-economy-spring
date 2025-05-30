package com.myeconomy.service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myeconomy.dto.LimiteMensalRequest;
import com.myeconomy.dto.LimiteMensalResponse;
import com.myeconomy.model.LimiteMensal;
import com.myeconomy.model.Usuario;
import com.myeconomy.repository.LimiteMensalRepository;
import com.myeconomy.repository.UsuarioRepository;

@Service
public class LimiteMensalService {

    @Autowired
    private LimiteMensalRepository limiteMensalRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public LimiteMensalResponse criar(LimiteMensalRequest request) {
        validarMesReferencia(request.getMesReferencia());
        
        Usuario usuario = getUsuarioAutenticado();
        
        if (limiteMensalRepository.existsByUsuarioAndMesReferencia(usuario, request.getMesReferencia())) {
            throw new IllegalArgumentException("Já existe um limite cadastrado para este mês");
        }
        
        LimiteMensal limite = new LimiteMensal();
        limite.setValor(request.getValor());
        limite.setMesReferencia(request.getMesReferencia());
        limite.setUsuario(usuario);
        
        limite = limiteMensalRepository.save(limite);
        return converterParaResponse(limite);
    }

    @Transactional
    public LimiteMensalResponse atualizar(Long id, LimiteMensalRequest request) {
        validarMesReferencia(request.getMesReferencia());
        
        LimiteMensal limite = buscarLimiteDoUsuario(id);
        validarMesReferencia(limite.getMesReferencia());
        
        limite.setValor(request.getValor());
        limite.setMesReferencia(request.getMesReferencia());
        
        limite = limiteMensalRepository.save(limite);
        return converterParaResponse(limite);
    }

    @Transactional
    public void excluir(Long id) {
        LimiteMensal limite = buscarLimiteDoUsuario(id);
        validarMesReferencia(limite.getMesReferencia());
        
        limiteMensalRepository.delete(limite);
    }

    @Transactional(readOnly = true)
    public LimiteMensalResponse buscarPorMes(YearMonth mesReferencia) {
        Usuario usuario = getUsuarioAutenticado();
        return limiteMensalRepository.findByUsuarioAndMesReferencia(usuario, mesReferencia)
            .map(this::converterParaResponse)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<LimiteMensalResponse> listarLimitesAPartirDoMes(YearMonth mesReferencia) {
        Usuario usuario = getUsuarioAutenticado();
        return limiteMensalRepository.findByUsuarioAndMesReferenciaGreaterThanEqualOrderByMesReferenciaDesc(
                usuario, mesReferencia)
            .stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }

    private void validarMesReferencia(YearMonth mesReferencia) {
        YearMonth mesAtual = YearMonth.now();
        if (mesReferencia.isBefore(mesAtual)) {
            throw new IllegalArgumentException("Não é possível criar/editar limites para meses anteriores ao atual");
        }
    }

    private LimiteMensal buscarLimiteDoUsuario(Long id) {
        Usuario usuario = getUsuarioAutenticado();
        return limiteMensalRepository.findById(id)
            .filter(l -> l.getUsuario().getId().equals(usuario.getId()))
            .orElseThrow(() -> new IllegalArgumentException("Limite mensal não encontrado"));
    }

    private Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private LimiteMensalResponse converterParaResponse(LimiteMensal limite) {
        LimiteMensalResponse response = new LimiteMensalResponse();
        response.setId(limite.getId());
        response.setValor(limite.getValor());
        response.setMesReferencia(limite.getMesReferencia());
        response.setDataCriacao(limite.getDataCriacao());
        response.setDataAtualizacao(limite.getDataAtualizacao());
        return response;
    }
} 