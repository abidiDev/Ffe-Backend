package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.dtos.PrescriptionMedicationsDTO;
import com.pfe.prescription_microservice.entities.Prescription;
import com.pfe.prescription_microservice.services.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prescriptions")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PrescriptionController {
    PrescriptionService prescriptionService;

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id).orElse(null);
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        return prescriptionService.updatePrescription(id, prescription);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
    }

    @GetMapping("/count")
    public long countPrescriptions() {
        return prescriptionService.countPrescriptions();
    }

    @PostMapping("/assign-medications")
    public Prescription assignMedicationsToPrescription(@RequestBody PrescriptionMedicationsDTO dto) {
        return prescriptionService.assignMedicationsToPrescription(dto);
    }

    @GetMapping("/needed-medications")
    public Map<String, Integer> getMedicationsToSupply() {
        return prescriptionService.getMedicationsToSupply();
    }
}
