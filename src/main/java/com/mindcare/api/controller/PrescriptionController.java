package com.mindcare.api.controller;

import com.mindcare.api.dto.PrescriptionDTO;
import com.mindcare.api.model.Prescription;
import com.mindcare.api.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescricoes")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> criar(@RequestBody PrescriptionDTO dto) {
        Prescription nova = prescriptionService.salvar(dto);
        return ResponseEntity.ok(nova);
    }
}
