package com.myeconomy.controller;

import com.myeconomy.dto.JwtResponse;
import com.myeconomy.dto.LoginRequest;
import com.myeconomy.dto.SignupRequest;
import com.myeconomy.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> autenticar(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.autenticar(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> registrar(@Valid @RequestBody SignupRequest signupRequest) {
        authService.registrar(signupRequest);
        return ResponseEntity.ok().build();
    }
} 