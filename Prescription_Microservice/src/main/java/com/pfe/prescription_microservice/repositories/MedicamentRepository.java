package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    Medicament findByNom(String allergieNom);
}