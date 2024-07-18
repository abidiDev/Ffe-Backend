package com.pfe.prescription_microservice.controllers;

import java.util.List;

import com.pfe.prescription_microservice.entities.Medicament;
import com.pfe.prescription_microservice.services.MedicamentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medicaments")
@AllArgsConstructor
public class MedicamentController {
     MedicamentService medicamentService;

    @GetMapping
    public List<Medicament> getAllMedicaments() {
        return medicamentService.getAllMedicaments();
    }

    @GetMapping("/{id}")
    public Medicament getMedicamentById(@PathVariable Long id) {
        return medicamentService.getMedicamentById(id).orElse(null);
    }

    @PostMapping
    public Medicament createMedicament(@RequestBody Medicament medicament) {
        return medicamentService.createMedicament(medicament);
    }

    @PutMapping("/{id}")
    public Medicament updateMedicament(@PathVariable Long id, @RequestBody Medicament medicament) {
        return medicamentService.updateMedicament(id, medicament);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
    }

    @GetMapping("/count")
    public long countMedicaments() {
        return medicamentService.countMedicaments();
    }
}