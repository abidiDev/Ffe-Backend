package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.services.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class StatisticsController {

   StatisticsService statisticsService;



    @GetMapping
    public Map<String, Object> getStatistics() {
        return statisticsService.getStatistics();
    }
}
