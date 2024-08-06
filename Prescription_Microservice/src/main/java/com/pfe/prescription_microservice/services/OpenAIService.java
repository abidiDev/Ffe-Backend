package com.pfe.prescription_microservice.services;

import reactor.core.publisher.Mono;

public interface OpenAIService {
    Mono<String> getExplanation(String symptoms);
}