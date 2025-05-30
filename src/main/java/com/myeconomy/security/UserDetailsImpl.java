package com.myeconomy.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myeconomy.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;
    private String nome;
    @JsonIgnore
    private String senha;
    private LocalDate dataNascimento;

    public UserDetailsImpl(Usuario usuario) {
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
        this.dataNascimento = usuario.getDataNascimento();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
} 