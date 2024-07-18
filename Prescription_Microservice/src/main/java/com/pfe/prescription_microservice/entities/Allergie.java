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
public class Allergie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medicament_id")
    private Medicament medicament;

    private Date dateDetection;
    private String description;

    // Getters and Setters
}

