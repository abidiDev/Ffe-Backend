package com.pfe.prescription_microservice.entities;
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
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String dosageRecommande;
    private String effetsSecondaires;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Prescription> prescriptions;

    @OneToMany(mappedBy = "medicament")
    private Set<Allergie> allergies;

    // Getters and Setters
}