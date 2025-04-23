package com.mindcare.api.controller;

import com.mindcare.api.dto.AppointmentDTO;
import com.mindcare.api.model.Appointment;
import com.mindcare.api.security.AuthUtil;
import com.mindcare.api.security.UserDetailsImpl;
import com.mindcare.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @PostMapping
    public ResponseEntity<Appointment> agendar(@RequestBody AppointmentDTO dto) {
        Appointment consulta = appointmentService.agendar(dto);
        return ResponseEntity.ok(consulta);
    }

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("/profissional/me")
    public ResponseEntity<List<Appointment>> listarDoProfissionalLogado() {
        UserDetailsImpl authUser = AuthUtil.getUsuarioAutenticado();
        List<Appointment> consultas = appointmentService.listarFuturosDoProfissional(authUser.getUsername());
        return ResponseEntity.ok(consultas);
    }
}
