package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.dtos.PatientDTO;
import com.pfe.prescription_microservice.entities.*;
import com.pfe.prescription_microservice.repositories.*;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {
     PatientRepository patientRepository;
    PrescriptionRepository prescriptionRepository;
    SymptomeRepository symptomeRepository;
    MedicamentRepository medicamentRepository;
    AllergieRepository allergieRepository;



    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient createPatientWithDetails(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setNom(patientDTO.getNom());
        patient.setPrenom(patientDTO.getPrenom());
        patient.setDateNaissance(patientDTO.getDateNaissance());
        patient.setEmail(patientDTO.getEmail());
        patient.setTelephone(patientDTO.getTelephone());
        patient.setAdresse(patientDTO.getAdresse());
        patientRepository.save(patient);

        Set<Prescription> prescriptions = new HashSet<>();
        Set<Allergie> allergies = new HashSet<>();

        if (patientDTO.getMaladies() != null) {
            for (PatientDTO.MaladieDTO maladieDTO : patientDTO.getMaladies()) {
                if (maladieDTO.getSymptomes() != null) {
                    for (PatientDTO.SymptomeDTO symptomeDTO : maladieDTO.getSymptomes()) {
                        Prescription prescription = new Prescription();
                        prescription.setPatient(patient);

                        Symptome symptome = new Symptome();
                        symptome.setDescription(symptomeDTO.getDescription());
                        symptomeRepository.save(symptome);

                        prescription.setSymptome(symptome);
                        prescription.setMaladie(maladieDTO.getNom());
                        prescription.setMedicalService(maladieDTO.getMedicalService());
                        prescription.setDatePrescription(LocalDate.now());
                        prescription.setStatut("En cours");
                        prescriptionRepository.save(prescription);

                        prescriptions.add(prescription);
                    }
                }
            }
        }

        if (patientDTO.getAllergies() != null) {
            for (PatientDTO.AllergieDTO allergieDTO : patientDTO.getAllergies()) {
                Medicament medicament = medicamentRepository.findByNom(allergieDTO.getMedicament());
                if (medicament == null) {
                    medicament = new Medicament();
                    medicament.setNom(allergieDTO.getMedicament());
                    medicamentRepository.save(medicament);
                }

                Allergie allergie = new Allergie();
                allergie.setPatient(patient);
                allergie.setMedicament(medicament);
                allergie.setDescription(allergieDTO.getDescription());
                allergie.setDateDetection(new Date());
                allergieRepository.save(allergie);

                allergies.add(allergie);
            }
        }

        patient.setPrescriptions(prescriptions);
        patient.setAllergies(allergies);

        return patientRepository.save(patient);
    }
    public Patient updatePatient(Long id, Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            patient.setId(id);
            return patientRepository.save(patient);
        } else {
            return null;
        }
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

}