package com.mindcare.api.controller;

import com.mindcare.api.model.Appointment;
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

    @GetMapping("/horarios-livres")
    @PreAuthorize("hasAuthority('PACIENTE')")
    public List<Appointment> listarHorariosLivres() {
        return appointmentService.listarHorariosLivres();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PACIENTE')")
    public ResponseEntity<?> agendarConsulta(@RequestBody AgendarDTO dto) {
        try {
            Appointment consulta = appointmentService.agendarConsulta(dto.getHorarioId());
            return ResponseEntity.ok(consulta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE')")
    public ResponseEntity<?> desmarcarConsulta(@PathVariable Long id) {
        try {
            appointmentService.desmarcarConsulta(id);
            return ResponseEntity.ok("Consulta desmarcada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DTO interno
    public static class AgendarDTO {
        private Long horarioId;

        public Long getHorarioId() {
            return horarioId;
        }

        public void setHorarioId(Long horarioId) {
            this.horarioId = horarioId;
        }
    }
}
