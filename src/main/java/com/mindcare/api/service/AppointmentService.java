package com.mindcare.api.service;

import com.mindcare.api.model.Appointment;
import com.mindcare.api.model.Patient;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.AppointmentRepository;
import com.mindcare.api.repository.PatientRepository;
import com.mindcare.api.security.AuthUtil;
import com.mindcare.api.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
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

    public List<Appointment> listarHorariosLivres() {
        return appointmentRepository.findHorariosLivres(LocalDateTime.now());
    }

    @Transactional
    public Appointment agendarConsulta(Long horarioId) throws Exception {
        UserDetailsImpl authUser = AuthUtil.getUsuarioAutenticado();
        User user = authUser.getUser();
        Long userId = user.getId();

        Optional<Patient> pacienteOpt = patientRepository.findById(userId);
        if (pacienteOpt.isEmpty()) {
            throw new Exception("Paciente não encontrado");
        }

        Optional<Appointment> horarioOpt = appointmentRepository.findById(horarioId);
        if (horarioOpt.isEmpty()) {
            throw new Exception("Horário não encontrado");
        }

        Appointment horario = horarioOpt.get();
        if (horario.getPatient() != null) {
            throw new Exception("Horário já ocupado");
        }

        horario.setPatient(pacienteOpt.get());
        horario.setStatus("AGENDADA");
        return appointmentRepository.save(horario);
    }

    @Transactional
    public void desmarcarConsulta(Long consultaId) throws Exception {
        Appointment consulta = appointmentRepository.findById(consultaId)
                .orElseThrow(() -> new Exception("Consulta não encontrada"));

        LocalDateTime agora = LocalDateTime.now();
        if (consulta.getDataHora().minusHours(12).isBefore(agora)) {
            throw new Exception("Cancelamento só permitido com 12 horas de antecedência.");
        }

        consulta.setPatient(null);
        consulta.setStatus(null);
        consulta.setObservacoes(null);

        appointmentRepository.save(consulta);
    }
}
