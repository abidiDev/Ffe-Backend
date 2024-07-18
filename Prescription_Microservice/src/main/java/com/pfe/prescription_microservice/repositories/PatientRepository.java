package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}