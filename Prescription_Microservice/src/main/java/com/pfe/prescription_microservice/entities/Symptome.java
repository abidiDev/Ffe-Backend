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
public class Symptome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "symptome")
    private Set<Prescription> prescriptions;

    // Getters and Setters
}
