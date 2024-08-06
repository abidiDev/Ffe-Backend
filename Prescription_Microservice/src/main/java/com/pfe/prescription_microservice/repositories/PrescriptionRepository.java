package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.MedicalService;
import com.pfe.prescription_microservice.entities.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByMedicalService(MedicalService medicalService);

    List<Prescription> findByDatePrescriptionBetween(LocalDate start, LocalDate end);
    long countByDatePrescriptionAfter(LocalDate date);
}