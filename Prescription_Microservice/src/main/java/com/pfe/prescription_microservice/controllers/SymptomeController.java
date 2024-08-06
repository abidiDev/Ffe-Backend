package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.entities.Symptome;
import com.pfe.prescription_microservice.services.SymptomeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/symptomes")
@CrossOrigin(origins = "http://localhost:4200")

public class SymptomeController {
     SymptomeService symptomeService;

    @GetMapping
    public List<Symptome> getAllSymptomes() {
        return symptomeService.getAllSymptomes();
    }

    @GetMapping("/{id}")
    public Symptome getSymptomeById(@PathVariable Long id) {
        return symptomeService.getSymptomeById(id).orElse(null);
    }

    @PostMapping
    public Symptome createSymptome(@RequestBody Symptome symptome) {
        return symptomeService.createSymptome(symptome);
    }

    @PutMapping("/{id}")
    public Symptome updateSymptome(@PathVariable Long id, @RequestBody Symptome symptome) {
        return symptomeService.updateSymptome(id, symptome);
    }

    @DeleteMapping("/{id}")
    public void deleteSymptome(@PathVariable Long id) {
        symptomeService.deleteSymptome(id);
    }

    @GetMapping("/count")
    public long countSymptomes() {
        return symptomeService.countSymptomes();
    }
}