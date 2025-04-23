package com.mindcare.api.controller;

import com.mindcare.api.dto.MedicalRecordDTO;
import com.mindcare.api.model.MedicalRecord;
import com.mindcare.api.security.AuthUtil;
import com.mindcare.api.security.UserDetailsImpl;
import com.mindcare.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping
    public ResponseEntity<MedicalRecord> salvar(@RequestBody MedicalRecordDTO dto) {
        MedicalRecord novo = medicalRecordService.salvar(dto);
        return ResponseEntity.ok(novo);
    }

    @PreAuthorize("hasAuthority('PACIENTE')")
    @GetMapping("/me")
    public ResponseEntity<List<MedicalRecord>> listarMeusRegistros() {
        UserDetailsImpl authUser = AuthUtil.getUsuarioAutenticado();
        List<MedicalRecord> registros = medicalRecordService.listarPorEmailDoPaciente(authUser.getUsername());
        return ResponseEntity.ok(registros);
    }
}

