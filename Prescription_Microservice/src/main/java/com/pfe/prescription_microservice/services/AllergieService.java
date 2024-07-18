package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.Allergie;

import java.util.List;
import java.util.Optional;

public interface AllergieService {
    List<Allergie> getAllAllergies();
    Optional<Allergie> getAllergieById(Long id);
    Allergie createAllergie(Allergie allergie);
    Allergie updateAllergie(Long id, Allergie allergie);
    void deleteAllergie(Long id);
    long countAllergies();
}