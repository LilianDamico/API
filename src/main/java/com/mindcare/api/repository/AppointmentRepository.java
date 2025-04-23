package com.mindcare.api.repository;

import com.mindcare.api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByProfessionalEmailAndDataHoraAfterOrderByDataHoraAsc(String email, LocalDateTime dataHora);
}
