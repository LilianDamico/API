package com.mindcare.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "clinics")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da clínica é obrigatório")
    private String nome;

    private String endereco;

    private String telefone;

    // Mapeamento inverso para usuários da clínica
    @OneToMany(mappedBy = "clinic")
    private List<User> usuarios;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public List<User> getUsuarios() { return usuarios; }
    public void setUsuarios(List<User> usuarios) { this.usuarios = usuarios; }
}
