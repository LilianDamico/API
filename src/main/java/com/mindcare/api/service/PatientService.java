package com.mindcare.api.service;

import com.mindcare.api.dto.PatientDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.model.Patient;
import com.mindcare.api.repository.ClinicRepository;
import com.mindcare.api.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Patient criar(PatientDTO dto) {
        Patient paciente = new Patient();
        paciente.setNome(dto.nome);
        paciente.setCpf(dto.cpf);
        paciente.setDataNascimento(dto.dataNascimento);
        paciente.setGenero(dto.genero);
        paciente.setContatoEmergencia(dto.contatoEmergencia);
        paciente.setTelefone(dto.telefone);
        paciente.setEmail(dto.email);

        if (dto.clinicId != null) {
            Optional<Clinic> clinicOpt = clinicRepository.findById(dto.clinicId);
            clinicOpt.ifPresent(paciente::setClinic);
        }

        return patientRepository.save(paciente);
    }

    public List<Patient> listarTodos() {
        return patientRepository.findAll();
    }
}
