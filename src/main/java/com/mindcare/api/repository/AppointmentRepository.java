package com.mindcare.api.repository;

import com.mindcare.api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient IS NULL AND a.dataHora > :dataAtual")
    List<Appointment> findHorariosLivres(LocalDateTime dataAtual);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId")
    List<Appointment> findByPatientId(Long patientId);

    @Query("SELECT a FROM Appointment a WHERE a.professional.id = :professionalId")
    List<Appointment> findByProfessionalId(Long professionalId);
}
