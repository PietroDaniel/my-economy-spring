package com.myeconomy.controller;

import com.myeconomy.dto.DespesaRequest;
import com.myeconomy.dto.DespesaResponse;
import com.myeconomy.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/despesas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @PostMapping
    public ResponseEntity<DespesaResponse> criar(@Valid @RequestBody DespesaRequest request) {
        return ResponseEntity.ok(despesaService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DespesaRequest request) {
        return ResponseEntity.ok(despesaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        despesaService.excluir(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mes/{mesReferencia}")
    public ResponseEntity<List<DespesaResponse>> listarPorMes(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth mesReferencia) {
        return ResponseEntity.ok(despesaService.listarPorMes(mesReferencia));
    }
} 