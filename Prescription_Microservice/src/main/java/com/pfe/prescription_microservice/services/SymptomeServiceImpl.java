package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Symptome;
import com.pfe.prescription_microservice.repositories.SymptomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SymptomeServiceImpl implements SymptomeService {
     SymptomeRepository symptomeRepository;

    @Override
    public List<Symptome> getAllSymptomes() {
        return symptomeRepository.findAll();
    }

    @Override
    public Optional<Symptome> getSymptomeById(Long id) {
        return symptomeRepository.findById(id);
    }

    @Override
    public Symptome createSymptome(Symptome symptome) {
        return symptomeRepository.save(symptome);
    }

    @Override
    public Symptome updateSymptome(Long id, Symptome symptome) {
        Optional<Symptome> existingSymptome = symptomeRepository.findById(id);
        if (existingSymptome.isPresent()) {
            symptome.setId(id);
            return symptomeRepository.save(symptome);
        } else {
            return null;
        }
    }

    @Override
    public void deleteSymptome(Long id) {
        symptomeRepository.deleteById(id);
    }


    @Override
    public long countSymptomes() {
        return symptomeRepository.count();
    }
}