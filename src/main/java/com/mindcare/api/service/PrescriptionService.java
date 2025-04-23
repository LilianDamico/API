package com.mindcare.api.service;

import com.mindcare.api.dto.PrescriptionDTO;
import com.mindcare.api.dto.MedicamentoInfoDTO;
import com.mindcare.api.model.*;
import com.mindcare.api.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AnvisaApiService anvisaApiService;

    public Prescription salvar(PrescriptionDTO dto) {
        Prescription p = new Prescription();
        p.setDescricao(dto.descricao);
        p.setData(dto.data);

        patientRepository.findById(dto.patientId).ifPresent(p::setPatient);
        userRepository.findById(dto.professionalId).ifPresent(p::setProfessional);
        if (dto.appointmentId != null) {
            appointmentRepository.findById(dto.appointmentId).ifPresent(p::setAppointment);
        }

        // Consulta à API de medicamentos e preenchimento automático
        try {
            MedicamentoInfoDTO info = anvisaApiService.consultar(dto.descricao);
            if (info != null) {
                p.setIndicacoes(info.indicacoes);
                p.setContraIndicacoes(info.contraIndicacoes);
                p.setEfeitosColaterais(info.efeitosColaterais);
            }
        } catch (Exception e) {
            // Em caso de falha na API, apenas registra no log
            System.out.println("Erro ao consultar API de medicamentos: " + e.getMessage());
        }

        return prescriptionRepository.save(p);
    }

    public List<Prescription> listarPorProfissional(Long profissionalId) {
        return prescriptionRepository.findByProfessionalId(profissionalId);
    }
    
    public Optional<Prescription> buscarPorId(Long id) {
        return prescriptionRepository.findById(id);
    }
    
}
