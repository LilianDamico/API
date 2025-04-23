package com.mindcare.api.repository;

import com.mindcare.api.model.Prescription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByProfessionalId(Long professionalId);

}
