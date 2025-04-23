package com.mindcare.api.dto;

public class DashboardDTO {
    public long totalPacientes;
    public long totalProfissionais;
    public long totalAgendamentos;
    public long totalPrescricoes;
    public long totalHistoricos;

    public DashboardDTO(long pacientes, long profissionais, long agendamentos, long prescricoes, long historicos) {
        this.totalPacientes = pacientes;
        this.totalProfissionais = profissionais;
        this.totalAgendamentos = agendamentos;
        this.totalPrescricoes = prescricoes;
        this.totalHistoricos = historicos;
    }
}
