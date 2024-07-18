package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Prescription;
import com.pfe.prescription_microservice.repositories.PrescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
     PrescriptionRepository prescriptionRepository;

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription updatePrescription(Long id, Prescription prescription) {
        Optional<Prescription> existingPrescription = prescriptionRepository.findById(id);
        if (existingPrescription.isPresent()) {
            prescription.setId(id);
            return prescriptionRepository.save(prescription);
        } else {
            return null;
        }
    }

    @Override
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public long countPrescriptions() {
        return prescriptionRepository.count();
    }
}
