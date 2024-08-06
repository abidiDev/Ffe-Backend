package com.pfe.prescription_microservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String email;
    private String telephone;
    private String adresse;
    private String historiqueMedical;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference

    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference

    private Set<Allergie> allergies;

    // Getters and Setters
}

