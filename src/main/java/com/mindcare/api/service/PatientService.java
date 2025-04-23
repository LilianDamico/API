package com.mindcare.api.service;

import com.mindcare.api.dto.PatientDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.model.Patient;
import com.mindcare.api.repository.ClinicRepository;
import com.mindcare.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Patient criar(PatientDTO dto) {
        Patient patient = new Patient();
        patient.setNome(dto.nome);
        patient.setCpf(dto.cpf);
        patient.setDataNascimento(dto.dataNascimento);
        patient.setGenero(dto.genero);
        patient.setContatoEmergencia(dto.contatoEmergencia);
        patient.setTelefone(dto.telefone);
        patient.setEmail(dto.email);

        Optional<Clinic> clinic = clinicRepository.findById(dto.clinicId);
        clinic.ifPresent(patient::setClinic);

        return patientRepository.save(patient);
    }
}
