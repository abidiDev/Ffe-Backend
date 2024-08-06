package com.pfe.prescription_microservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescriptionMedicationsDTO {
    private Long prescriptionId;
    private List<MedicationQuantityDTO> medications;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MedicationQuantityDTO {
        private Long medicationId;
        private int quantity;
    }
}