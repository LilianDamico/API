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

    /**
     * Agenda uma nova consulta com base nos dados fornecidos pelo DTO.
     * Busca e associa o Paciente e o Profissional às entidades de Appointment.
     *
     * @param dto AppointmentDTO contendo pacienteId, profissionalId, dataHora e observacoes.
     * @return A entidade Appointment salva.
     * @throws RuntimeException se o paciente ou profissional não for encontrado.
     */
    @Transactional
    public Appointment agendarNovaConsulta(AppointmentDTO dto) {
        // 1. Obter o Paciente
        // Decida se o pacienteId virá do DTO ou do usuário logado.
        // Se vier do usuário logado:
        // Long pacienteId = authUtil.getUsuarioAutenticado().getId();
        // Se vier do DTO (como o seu frontend está enviando):
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

    /**
     * Lista horários livres.
     * ATENÇÃO: A lógica para determinar "horários livres" precisa ser implementada
     * dentro do repositório ou aqui, consultando appointments existentes.
     * Por enquanto, este método é um placeholder.
     * Você pode precisar de parâmetros como 'data' e 'profissionalId' para filtrar.
     *
     * @return Uma lista de Appointments que representam horários livres (ou ajustes).
     */
    public List<Appointment> listarHorariosLivres() {
        // Exemplo: Buscar todos os agendamentos futuros (não significa "livres" diretamente)
        // return appointmentRepository.findByDataHoraAfter(LocalDateTime.now());
        // Para horários *realmente* livres, a lógica seria mais complexa,
        // envolvendo a consulta de horários já ocupados por profissional/clínica.
        return appointmentRepository.findAll(); // Placeholder: retorna tudo por enquanto
    }

    /**
     * Desmarca uma consulta existente.
     *
     * @param id O ID da consulta a ser desmarcada.
     * @throws RuntimeException se a consulta não for encontrada.
     */
    @Transactional
    public void desmarcarConsulta(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));

        // Altera o status da consulta para CANCELADA
        appointment.setStatus("CANCELADA");
        appointmentRepository.save(appointment); // Salva a atualização
    }

    // Você pode adicionar outros métodos conforme necessário, como:
    // public List<Appointment> listarConsultasDoPaciente(Long pacienteId) { ... }
    // public List<Appointment> listarConsultasDoProfissional(Long profissionalId) { ... }
}