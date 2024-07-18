package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Medicament;
import com.pfe.prescription_microservice.repositories.MedicamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicamentServiceImpl implements MedicamentService {
    private MedicamentRepository medicamentRepository;

    @Override
    public List<Medicament> getAllMedicaments() {
        return medicamentRepository.findAll();
    }

    @Override
    public Optional<Medicament> getMedicamentById(Long id) {
        return medicamentRepository.findById(id);
    }

    @Override
    public Medicament createMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    @Override
    public Medicament updateMedicament(Long id, Medicament medicament) {
        Optional<Medicament> existingMedicament = medicamentRepository.findById(id);
        if (existingMedicament.isPresent()) {
            medicament.setId(id);
            return medicamentRepository.save(medicament);
        } else {
            return null;
        }
    }

    @Override
    public void deleteMedicament(Long id) {
        medicamentRepository.deleteById(id);
    }

    @Override
    public long countMedicaments() {
        return medicamentRepository.count();
    }
}
