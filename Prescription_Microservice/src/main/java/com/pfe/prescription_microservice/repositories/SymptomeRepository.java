package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.Symptome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomeRepository extends JpaRepository<Symptome, Long> {
}