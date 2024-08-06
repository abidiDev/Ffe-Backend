package com.pfe.prescription_microservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    private String maladie;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "symptome_id")
    @JsonManagedReference

    private Symptome symptome;

    private LocalDate  datePrescription;
    private String statut;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PrescriptionMedicament> prescriptionMedicaments;

    private double cost;

    @Enumerated(EnumType.STRING)
    private MedicalService medicalService;

}