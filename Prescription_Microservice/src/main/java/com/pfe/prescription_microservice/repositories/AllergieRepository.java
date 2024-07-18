package com.pfe.prescription_microservice.repositories;

import com.pfe.prescription_microservice.entities.Allergie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergieRepository extends JpaRepository<Allergie, Long> {
}