package com.myeconomy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myeconomy.dto.JwtResponse;
import com.myeconomy.dto.LoginRequest;
import com.myeconomy.dto.SignupRequest;
import com.myeconomy.model.Usuario;
import com.myeconomy.repository.UsuarioRepository;
import com.myeconomy.security.JwtService;
import com.myeconomy.security.UserDetailsImpl;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public JwtResponse autenticar(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(
            jwt,
            "Bearer",
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getNome(),
            userDetails.getDataNascimento()
        );
    }

    @Transactional
    public void registrar(SignupRequest signupRequest) {
        if (usuarioRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email já está em uso!");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(signupRequest.getNome());
        usuario.setEmail(signupRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(signupRequest.getSenha()));
        usuario.setDataNascimento(signupRequest.getDataNascimento());

        usuarioRepository.save(usuario);
    }
} 