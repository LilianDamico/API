package com.mindcare.api.service;

import com.mindcare.api.dto.DashboardDTO;
import com.mindcare.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public DashboardDTO getEstatisticas() {
        long totalPacientes = patientRepository.count();
        long totalProfissionais = userRepository.countByTipo("PROFISSIONAL");
        long totalAgendamentos = appointmentRepository.count();
        long totalPrescricoes = prescriptionRepository.count();
        long totalHistoricos = medicalRecordRepository.count();

        return new DashboardDTO(
                totalPacientes,
                totalProfissionais,
                totalAgendamentos,
                totalPrescricoes,
                totalHistoricos
        );
    }
}

