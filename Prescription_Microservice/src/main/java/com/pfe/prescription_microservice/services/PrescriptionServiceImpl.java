package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.dtos.PrescriptionMedicationsDTO;
import com.pfe.prescription_microservice.entities.Medicament;
import com.pfe.prescription_microservice.entities.Prescription;
import com.pfe.prescription_microservice.entities.PrescriptionMedicament;
import com.pfe.prescription_microservice.repositories.MedicamentRepository;
import com.pfe.prescription_microservice.repositories.PrescriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
     PrescriptionRepository prescriptionRepository;
     MedicamentRepository  medicamentRepository;

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

    @Transactional
    public Prescription assignMedicationsToPrescription(PrescriptionMedicationsDTO dto) {
        Prescription prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        Set<PrescriptionMedicament> prescriptionMedications = new HashSet<>();

        for (PrescriptionMedicationsDTO.MedicationQuantityDTO medicationQuantityDTO : dto.getMedications()) {
            Medicament medicament = medicamentRepository.findById(medicationQuantityDTO.getMedicationId())
                    .orElseThrow(() -> new RuntimeException("Medicament not found"));

            PrescriptionMedicament prescriptionMedicament = new PrescriptionMedicament();
            prescriptionMedicament.setPrescription(prescription);
            prescriptionMedicament.setMedicament(medicament);
            prescriptionMedicament.setQuantite(medicationQuantityDTO.getQuantity());

            prescriptionMedications.add(prescriptionMedicament);
        }

        prescription.getPrescriptionMedicaments().clear();
        prescription.getPrescriptionMedicaments().addAll(prescriptionMedications);

        return prescriptionRepository.save(prescription);
    }

    public Map<String, Integer> getMedicationsToSupply() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        Map<String, Integer> neededMedications = new HashMap<>();

        for (Prescription prescription : prescriptions) {
            Set<PrescriptionMedicament> prescriptionMedicaments = prescription.getPrescriptionMedicaments();
            for (PrescriptionMedicament pm : prescriptionMedicaments) {
                String medName = pm.getMedicament().getNom();
                neededMedications.put(medName, neededMedications.getOrDefault(medName, 0) + pm.getQuantite());
            }
        }

        List<Medicament> availableMedications = medicamentRepository.findAll();
        for (Medicament med : availableMedications) {
            String medName = med.getNom();
            if (neededMedications.containsKey(medName)) {
                // Assuming medicament has a quantity field for available quantity
                int availableQuantity = med.getQuantite(); // Add this field in Medicament entity if not exists
                int neededQuantity = neededMedications.get(medName);
                if (availableQuantity >= neededQuantity) {
                    neededMedications.remove(medName);
                } else {
                    neededMedications.put(medName, neededQuantity - availableQuantity);
                }
            }
        }

        return neededMedications;
    }
}
