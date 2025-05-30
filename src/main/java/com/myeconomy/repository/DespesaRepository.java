package com.myeconomy.repository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myeconomy.model.Despesa;
import com.myeconomy.model.Usuario;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByUsuarioAndMesReferenciaOrderByDataCriacaoDesc(Usuario usuario, YearMonth mesReferencia);
    
    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.usuario = :usuario AND d.mesReferencia = :mesReferencia")
    BigDecimal calcularTotalDespesasPorMes(Usuario usuario, YearMonth mesReferencia);
    
    List<Despesa> findByUsuarioAndMesReferenciaGreaterThanEqualOrderByMesReferenciaDesc(
        Usuario usuario, 
        YearMonth mesReferencia
    );
    
    boolean existsByUsuarioAndMesReferencia(Usuario usuario, YearMonth mesReferencia);
} 