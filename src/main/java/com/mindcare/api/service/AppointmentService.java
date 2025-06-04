package com.mindcare.api.service;

import com.mindcare.api.dto.AppointmentDTO; // Importa o DTO que contém os dados da nova consulta
import com.mindcare.api.model.Appointment;
import com.mindcare.api.model.Patient;
import com.mindcare.api.model.User; // Para o profissional (assumindo que User é a entidade de profissionais)
import com.mindcare.api.repository.AppointmentRepository;
import com.mindcare.api.repository.PatientRepository;
import com.mindcare.api.repository.UserRepository; // Repositório para buscar o profissional
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository; // Injeção do UserRepository para buscar profissionais

    
    @Transactional
    public Appointment agendarNovaConsulta(AppointmentDTO dto) {
       
        Long pacienteId = dto.getPacienteId();

        Patient patient = patientRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + pacienteId));

        // 2. Obter o Profissional (User)
        User professional = userRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + dto.getProfissionalId()));

        // 3. Criar a nova entidade de Appointment
        Appointment newAppointment = new Appointment();
        newAppointment.setDataHora(dto.getDataHora());
        newAppointment.setObservacoes(dto.getObservacoes());
        newAppointment.setStatus("AGENDADA"); // Define o status inicial da consulta

        newAppointment.setPatient(patient);
        newAppointment.setProfessional(professional);

       
        return appointmentRepository.save(newAppointment);
    }

    
    public List<Appointment> listarHorariosLivres() {
        
        return appointmentRepository.findAll(); // Placeholder: retorna tudo por enquanto
    }

    
    @Transactional
    public void desmarcarConsulta(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        // Altera o status da consulta para CANCELADA
        appointment.setStatus("CANCELADA");
        appointmentRepository.save(appointment); // Salva a atualização
    }

    
}