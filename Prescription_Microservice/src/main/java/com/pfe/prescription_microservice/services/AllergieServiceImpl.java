package com.pfe.prescription_microservice.services;


import com.pfe.prescription_microservice.entities.Allergie;
import com.pfe.prescription_microservice.repositories.AllergieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AllergieServiceImpl implements AllergieService {
     AllergieRepository allergieRepository;

    @Override
    public List<Allergie> getAllAllergies() {
        return allergieRepository.findAll();
    }

    @Override
    public Optional<Allergie> getAllergieById(Long id) {
        return allergieRepository.findById(id);
    }

    @Override
    public Allergie createAllergie(Allergie allergie) {
        return allergieRepository.save(allergie);
    }

    @Override
    public Allergie updateAllergie(Long id, Allergie allergie) {
        Optional<Allergie> existingAllergie = allergieRepository.findById(id);
        if (existingAllergie.isPresent()) {
            allergie.setId(id);
            return allergieRepository.save(allergie);
        } else {
            return null;
        }
    }

    @Override
    public void deleteAllergie(Long id) {
        allergieRepository.deleteById(id);
    }

    @Override
    public long countAllergies() {
        return allergieRepository.count();
    }
}