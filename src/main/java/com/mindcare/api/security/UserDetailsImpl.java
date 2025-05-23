package com.mindcare.api.security;

import com.mindcare.api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private Long clinicId;

    private Collection<? extends GrantedAuthority> authorities;

    // ✅ Construtor usado para carregar o usuário autenticado
    public UserDetailsImpl(Long id, String nome, String email, String senha, String tipo, Long clinicId,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.clinicId = clinicId;
        this.authorities = authorities;
    }

    // ✅ Construtor usado no login com um objeto User
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
            user.getId(),
            user.getNome(),
            user.getEmail(),
            user.getSenha(),
            user.getTipo(),
            user.getClinic() != null ? user.getClinic().getId() : null,
            null // Se você tiver perfis/roles, substitua por uma lista de authorities
        );
    }

    // ✅ Getter necessário para AuthController e outros usos
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public User getUser() {
        // Apenas para compatibilidade com `getUser()` chamado em alguns serviços
        // Retorna um objeto User com os dados carregados
        User user = new User();
        user.setId(id);
        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setTipo(tipo);
        return user;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
