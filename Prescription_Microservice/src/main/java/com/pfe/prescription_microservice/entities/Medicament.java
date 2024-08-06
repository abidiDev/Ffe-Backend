package com.pfe.prescription_microservice.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String dosageRecommande;
    private String effetsSecondaires;
    private int quantite;


    @OneToMany(mappedBy = "medicament", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<PrescriptionMedicament> prescriptionMedicaments;

    @OneToMany(mappedBy = "medicament")
    @JsonManagedReference
    private Set<Allergie> allergies;

}