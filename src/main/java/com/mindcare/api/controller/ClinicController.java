package com.mindcare.api.controller;

import com.mindcare.api.dto.ClinicDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinicas")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping
    public ResponseEntity<Clinic> criar(@RequestBody ClinicDTO dto) {
        Clinic nova = clinicService.criarClinic(dto);
        return ResponseEntity.ok(nova);
    }
}
