package com.mindcare.api.dto;

import java.time.LocalDate;

public class MedicalRecordDTO {
    public LocalDate data;
    public String diagnostico;
    public String observacoes;
    public Long patientId;
    public Long professionalId;
    public Long prescriptionId; // opcional
}

