package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Symptome;

import java.util.List;
import java.util.Optional;

public interface SymptomeService {
    List<Symptome> getAllSymptomes();
    Optional<Symptome> getSymptomeById(Long id);
    Symptome createSymptome(Symptome symptome);
    Symptome updateSymptome(Long id, Symptome symptome);
    void deleteSymptome(Long id);
    long countSymptomes();
}
