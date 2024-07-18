package com.pfe.prescription_microservice.services;
import com.pfe.prescription_microservice.entities.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();
    Optional<Prescription> getPrescriptionById(Long id);
    Prescription createPrescription(Prescription prescription);
    Prescription updatePrescription(Long id, Prescription prescription);
    void deletePrescription(Long id);
    long countPrescriptions();
}