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
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "symptome_id")
    private Symptome symptome;

    private Date datePrescription;
    private String statut;

    @ManyToMany
    @JoinTable(
            name = "PrescriptionMedicament",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id")
    )
    private Set<Medicament> medicaments;

}