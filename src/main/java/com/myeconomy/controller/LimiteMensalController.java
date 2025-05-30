package com.myeconomy.controller;

import com.myeconomy.dto.LimiteMensalRequest;
import com.myeconomy.dto.LimiteMensalResponse;
import com.myeconomy.service.LimiteMensalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/limites")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LimiteMensalController {

    @Autowired
    private LimiteMensalService limiteMensalService;

    @PostMapping
    public ResponseEntity<LimiteMensalResponse> criar(@Valid @RequestBody LimiteMensalRequest request) {
        return ResponseEntity.ok(limiteMensalService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LimiteMensalResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LimiteMensalRequest request) {
        return ResponseEntity.ok(limiteMensalService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        limiteMensalService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mes/{mesReferencia}")
    public ResponseEntity<LimiteMensalResponse> buscarPorMes(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth mesReferencia) {
        LimiteMensalResponse limite = limiteMensalService.buscarPorMes(mesReferencia);
        return limite != null ? ResponseEntity.ok(limite) : ResponseEntity.notFound().build();
    }

    @GetMapping("/apartir/{mesReferencia}")
    public ResponseEntity<List<LimiteMensalResponse>> listarLimitesAPartirDoMes(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth mesReferencia) {
        return ResponseEntity.ok(limiteMensalService.listarLimitesAPartirDoMes(mesReferencia));
    }
} 