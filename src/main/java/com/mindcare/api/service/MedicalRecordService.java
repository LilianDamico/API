package com.mindcare.api.service;

import com.mindcare.api.dto.MedicalRecordDTO;
import com.mindcare.api.model.*;
import com.mindcare.api.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public MedicalRecord salvar(MedicalRecordDTO dto) {
        MedicalRecord record = new MedicalRecord();
        record.setData(dto.data);
        record.setDiagnostico(dto.diagnostico);
        record.setObservacoes(dto.observacoes);

        patientRepository.findById(dto.patientId).ifPresent(record::setPatient);
        userRepository.findById(dto.professionalId).ifPresent(record::setProfessional);
        if (dto.prescriptionId != null) {
            prescriptionRepository.findById(dto.prescriptionId).ifPresent(record::setPrescription);
        }

        return medicalRecordRepository.save(record);
    }

    public List<MedicalRecord> listarPorEmailDoPaciente(String email) {
        return medicalRecordRepository.findByPatientEmailOrderByDataDesc(email);
    }
    
}

