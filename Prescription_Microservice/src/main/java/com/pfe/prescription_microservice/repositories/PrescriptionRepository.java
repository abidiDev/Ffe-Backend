package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}