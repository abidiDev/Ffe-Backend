package com.pfe.prescription_microservice.dtos;

import com.pfe.prescription_microservice.entities.MedicalService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PatientDTO {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String telephone;
    private String adresse;
    private Set<MaladieDTO> maladies;
    private Set<AllergieDTO> allergies;

    @Getter
    @Setter
    public static class MaladieDTO {
        private String nom;
        private MedicalService medicalService;
        private Set<SymptomeDTO> symptomes;
    }

    @Getter
    @Setter
    public static class SymptomeDTO {
        private String description;
    }

    @Getter
    @Setter
    public static class AllergieDTO {
        private String medicament;
        private String description;
    }
}
