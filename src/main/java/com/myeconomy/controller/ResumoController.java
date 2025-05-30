package com.myeconomy.controller;

import com.myeconomy.dto.ResumoMensalResponse;
import com.myeconomy.service.ResumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/resumo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResumoController {

    @Autowired
    private ResumoService resumoService;

    @GetMapping("/mes/{mesReferencia}")
    public ResponseEntity<ResumoMensalResponse> gerarResumoMensal(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth mesReferencia) {
        return ResponseEntity.ok(resumoService.gerarResumoMensal(mesReferencia));
    }
} 