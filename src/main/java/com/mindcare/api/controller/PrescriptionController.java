package com.mindcare.api.controller;

import com.mindcare.api.dto.PrescriptionDTO;
import com.mindcare.api.model.Prescription;
import com.mindcare.api.service.PrescriptionService;
import com.mindcare.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescricoes")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Prescription> criar(@RequestBody PrescriptionDTO dto) {
        Prescription nova = prescriptionService.salvar(dto);
        return ResponseEntity.ok(nova);
    }

    @GetMapping("/profissional")
    public List<Prescription> listarPorProfissional() {
        Long profissionalId = userService.getIdUserLogado();
        return prescriptionService.listarPorProfissional(profissionalId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> buscarPorId(@PathVariable Long id) {
        return prescriptionService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
