package com.myeconomy.repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myeconomy.model.LimiteMensal;
import com.myeconomy.model.Usuario;

@Repository
public interface LimiteMensalRepository extends JpaRepository<LimiteMensal, Long> {
    Optional<LimiteMensal> findByUsuarioAndMesReferencia(Usuario usuario, YearMonth mesReferencia);
    
    List<LimiteMensal> findByUsuarioAndMesReferenciaGreaterThanEqualOrderByMesReferenciaDesc(
        Usuario usuario, 
        YearMonth mesReferencia
    );
    
    boolean existsByUsuarioAndMesReferencia(Usuario usuario, YearMonth mesReferencia);
} 