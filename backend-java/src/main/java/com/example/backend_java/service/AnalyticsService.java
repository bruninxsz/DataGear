package com.example.backend_java.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AnalyticsService {
    private final RestClient client;
    private final String apiKey;

    public AnalyticsService(
            RestClient.Builder builder,
            @Value("${analytics.python.base-url:http://localhost:8000}") String baseUrl,
            @Value("${analytics.python.api-key:}") String apiKey) {
        this.client = builder.baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public Map<String, Object> processarImportacao(List<Map<String, Object>> linhas) {
        if (linhas == null || linhas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Envie pelo menos uma linha para processamento.");
        }
        try {
            return client.post()
                    .uri("/etl")
                    .headers(headers -> adicionarChave(headers))
                    .body(Map.of("recebidas", linhas.size(), "linhas", linhas))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
        }
    }

    public Map<String, Object> obterDashboard() {
        try {
            return client.get()
                    .uri("/dashboard")
                    .headers(this::adicionarChave)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientResponseException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
        }
    }

    private void adicionarChave(HttpHeaders headers) {
        if (apiKey != null && !apiKey.isBlank()) {
            headers.set("X-ETL-Key", apiKey);
        }
    }
}
