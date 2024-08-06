package com.pfe.prescription_microservice.controllers;

import com.pfe.prescription_microservice.dtos.SymptomsRequest;
import com.pfe.prescription_microservice.services.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/openai")
@AllArgsConstructor

public class OpenAIController {

     OpenAIService openAIService;





    @GetMapping("/{prompt}")
    public Mono<String> getExplanation(@PathVariable String prompt) {
        System.out.println("Received prompt: " + prompt);  // Debugging statement
        return openAIService.getExplanation(prompt);
    }
}

