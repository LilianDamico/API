package com.mindcare.api.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
    public LocalDateTime dataHora;
    public String status;
    public String observacoes;
    public Long patientId;
    public Long professionalId;
    public Long clinicId;
}
