package com.mindcare.api.service;

import com.mindcare.api.dto.ClinicDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public Clinic criarClinic(ClinicDTO dto) {
        Clinic clinic = new Clinic();
        clinic.setNome(dto.nome);
        clinic.setEndereco(dto.endereco);
        clinic.setTelefone(dto.telefone);
        return clinicRepository.save(clinic);
    }
}
