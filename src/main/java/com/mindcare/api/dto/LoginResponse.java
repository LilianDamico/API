package com.mindcare.api.dto;

public class LoginResponse {
    private String token;
    private String nome;
    private String tipo;

    public LoginResponse(String token, String nome, String tipo) {
        this.token = token;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }
}
