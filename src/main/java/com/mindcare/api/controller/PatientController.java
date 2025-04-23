package com.mindcare.api.controller;

import com.mindcare.api.dto.PatientDTO;
import com.mindcare.api.model.Patient;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.PatientRepository;
import com.mindcare.api.security.AuthUtil;
import com.mindcare.api.security.UserDetailsImpl;
import com.mindcare.api.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping
    public ResponseEntity<Patient> cadastrar(@RequestBody PatientDTO dto) {
        Patient novo = patientService.criar(dto);
        return ResponseEntity.ok(novo);
    }

    @PreAuthorize("hasAuthority('PACIENTE')")
    @GetMapping("/me")
    public ResponseEntity<Patient> getPacienteLogado() {
        UserDetailsImpl authUser = AuthUtil.getUsuarioAutenticado();
        User user = authUser.getUser();

        Optional<Patient> paciente = patientRepository.findByEmail(user.getEmail());
        return paciente.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
}
