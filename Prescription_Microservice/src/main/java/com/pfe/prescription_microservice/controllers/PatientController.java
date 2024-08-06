package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.dtos.PatientDTO;
import com.pfe.prescription_microservice.entities.Patient;
import com.pfe.prescription_microservice.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id).orElse(null);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }
    @PostMapping("/{create_with_details}")

    Patient createPatientWithDetails(@RequestBody PatientDTO patientDTO) {return patientService.createPatientWithDetails(patientDTO);}

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

}