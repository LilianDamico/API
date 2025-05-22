package com.mindcare.api.security;

import com.mindcare.api.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getNome() {
        return user.getNome();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getTipo() {
        return user.getTipo();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // ou coleções específicas se tiver papéis
    }

    @Override
    public String getPassword() {
        return user.getSenha();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
