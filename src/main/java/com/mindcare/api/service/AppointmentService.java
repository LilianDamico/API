package com.mindcare.api.service;

import com.mindcare.api.dto.AppointmentDTO;
import com.mindcare.api.model.*;
import com.mindcare.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Appointment agendar(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setDataHora(dto.dataHora);
        appointment.setStatus(dto.status);
        appointment.setObservacoes(dto.observacoes);

        Optional<Patient> patient = patientRepository.findById(dto.patientId);
        Optional<User> professional = userRepository.findById(dto.professionalId);
        Optional<Clinic> clinic = clinicRepository.findById(dto.clinicId);

        patient.ifPresent(appointment::setPatient);
        professional.ifPresent(appointment::setProfessional);
        clinic.ifPresent(appointment::setClinic);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> listarFuturosDoProfissional(String email) {
        return appointmentRepository.findByProfessionalEmailAndDataHoraAfterOrderByDataHoraAsc(
            email,
            LocalDateTime.now()
        );
    }

}
