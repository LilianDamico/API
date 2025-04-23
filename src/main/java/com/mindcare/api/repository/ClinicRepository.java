package com.mindcare.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mindcare.api.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    

}
