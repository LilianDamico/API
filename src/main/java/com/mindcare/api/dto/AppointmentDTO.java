package com.mindcare.api.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long pacienteId;
    private Long profissionalId; // ID do profissional selecionado no frontend
    private LocalDateTime dataHora;
    private String observacoes;

    // Construtor padrão é importante para a desserialização do JSON
    public AppointmentDTO() {
    }

    // Getters e Setters para todos os campos
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}