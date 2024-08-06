package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.entities.Allergie;
import com.pfe.prescription_microservice.services.AllergieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/allergies")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class AllergieController {
     AllergieService allergieService;

    @GetMapping
    public List<Allergie> getAllAllergies() {
        return allergieService.getAllAllergies();
    }

    @GetMapping("/{id}")
    public Allergie getAllergieById(@PathVariable Long id) {
        return allergieService.getAllergieById(id).orElse(null);
    }

    @PostMapping
    public Allergie createAllergie(@RequestBody Allergie allergie) {
        return allergieService.createAllergie(allergie);
    }

    @PutMapping("/{id}")
    public Allergie updateAllergie(@PathVariable Long id, @RequestBody Allergie allergie) {
        return allergieService.updateAllergie(id, allergie);
    }

    @DeleteMapping("/{id}")
    public void deleteAllergie(@PathVariable Long id) {
        allergieService.deleteAllergie(id);
    }

    @GetMapping("/count")
    public long countAllergies() {
        return allergieService.countAllergies();
    }
}
