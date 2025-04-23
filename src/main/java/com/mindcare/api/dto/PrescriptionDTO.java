package com.mindcare.api.dto;

import java.time.LocalDate;

public class PrescriptionDTO {
    public String descricao;
    public LocalDate data;
    public Long patientId;
    public Long professionalId;
    public Long appointmentId; // pode ser null
}
