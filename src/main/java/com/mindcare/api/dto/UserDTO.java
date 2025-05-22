package com.mindcare.api.dto;

public class UserDTO {

    private String nome;
    private String email;
    private String senha;
    private String tipo;
    private Long clinicId;

    // Construtores
    public UserDTO() {}

    public UserDTO(String nome, String email, String senha, String tipo, Long clinicId) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.clinicId = clinicId;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getClinicId() { return clinicId; }
    public void setClinicId(Long clinicId) { this.clinicId = clinicId; }
}
