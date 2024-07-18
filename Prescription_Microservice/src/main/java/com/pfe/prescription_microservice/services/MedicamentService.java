package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Medicament;

import java.util.List;
import java.util.Optional;

public interface MedicamentService {
    List<Medicament> getAllMedicaments();
    Optional<Medicament> getMedicamentById(Long id);
    Medicament createMedicament(Medicament medicament);
    Medicament updateMedicament(Long id, Medicament medicament);
    void deleteMedicament(Long id);
    long countMedicaments();
}