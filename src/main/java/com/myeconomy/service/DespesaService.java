package com.myeconomy.service;

import com.myeconomy.dto.DespesaRequest;
import com.myeconomy.dto.DespesaResponse;
import com.myeconomy.model.Despesa;
import com.myeconomy.model.Usuario;
import com.myeconomy.repository.DespesaRepository;
import com.myeconomy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public DespesaResponse criar(DespesaRequest request) {
        validarMesReferencia(request.getMesReferencia());
        
        Usuario usuario = getUsuarioAutenticado();
        
        Despesa despesa = new Despesa();
        despesa.setDescricao(request.getDescricao());
        despesa.setValor(request.getValor());
        despesa.setMesReferencia(request.getMesReferencia());
        despesa.setUsuario(usuario);
        
        despesa = despesaRepository.save(despesa);
        return converterParaResponse(despesa);
    }

    @Transactional
    public DespesaResponse atualizar(Long id, DespesaRequest request) {
        validarMesReferencia(request.getMesReferencia());
        
        Despesa despesa = buscarDespesaDoUsuario(id);
        validarMesReferencia(despesa.getMesReferencia());
        
        despesa.setDescricao(request.getDescricao());
        despesa.setValor(request.getValor());
        despesa.setMesReferencia(request.getMesReferencia());
        
        despesa = despesaRepository.save(despesa);
        return converterParaResponse(despesa);
    }

    @Transactional
    public void excluir(Long id) {
        Despesa despesa = buscarDespesaDoUsuario(id);
        validarMesReferencia(despesa.getMesReferencia());
        
        despesaRepository.delete(despesa);
    }

    @Transactional(readOnly = true)
    public List<DespesaResponse> listarPorMes(YearMonth mesReferencia) {
        Usuario usuario = getUsuarioAutenticado();
        return despesaRepository.findByUsuarioAndMesReferenciaOrderByDataCriacaoDesc(usuario, mesReferencia)
            .stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }

    private void validarMesReferencia(YearMonth mesReferencia) {
        YearMonth mesAtual = YearMonth.now();
        if (mesReferencia.isBefore(mesAtual)) {
            throw new IllegalArgumentException("Não é possível criar/editar despesas para meses anteriores ao atual");
        }
    }

    private Despesa buscarDespesaDoUsuario(Long id) {
        Usuario usuario = getUsuarioAutenticado();
        return despesaRepository.findById(id)
            .filter(d -> d.getUsuario().getEmail().equals(usuario.getEmail()))
            .orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada"));
    }

    private Usuario getUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    private DespesaResponse converterParaResponse(Despesa despesa) {
        DespesaResponse response = new DespesaResponse();
        response.setId(despesa.getId());
        response.setDescricao(despesa.getDescricao());
        response.setValor(despesa.getValor());
        response.setMesReferencia(despesa.getMesReferencia());
        response.setDataCriacao(despesa.getDataCriacao());
        response.setDataAtualizacao(despesa.getDataAtualizacao());
        return response;
    }
} 