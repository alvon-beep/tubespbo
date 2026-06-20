package com.ecotukar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrsMatrixClient {
    private static final Logger log = LoggerFactory.getLogger(OrsMatrixClient.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ors.api.key:}")
    private String apiKey;

    /**
     * Fetch duration matrix from OpenRouteService.
     * Coordinates list should contain double[] where element 0 is latitude and element 1 is longitude.
     * Returns a 2D array double[i][j] representing duration (in seconds) from location i to location j.
     */
    public double[][] getDurationMatrix(List<double[]> coordinates) {
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.contains("API_KEY_KAMU")) {
            throw new IllegalStateException("OpenRouteService API key is missing or placeholder");
        }

        try {
            String url = "https://api.openrouteservice.org/v2/matrix/driving-car";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", apiKey);

            // Construct locations: [[lng, lat], [lng, lat], ...]
            // Note: ORS uses [longitude, latitude] ordering
            List<List<Double>> locations = new ArrayList<>();
            for (double[] coord : coordinates) {
                locations.add(List.of(coord[1], coord[0]));
            }

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("locations", locations);
            requestBody.put("metrics", List.of("duration"));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            Map<?, ?> body = response.getBody();
            if (body != null && body.containsKey("durations")) {
                List<?> rawDurations = (List<?>) body.get("durations");
                int size = rawDurations.size();
                double[][] matrix = new double[size][size];
                for (int i = 0; i < size; i++) {
                    List<?> row = (List<?>) rawDurations.get(i);
                    for (int j = 0; j < size; j++) {
                        if (row.get(j) != null) {
                            matrix[i][j] = Double.parseDouble(row.get(j).toString());
                        } else {
                            matrix[i][j] = Double.MAX_VALUE; // fallback for unreachable points
                        }
                    }
                }
                log.info("Successfully fetched {}x{} duration matrix from ORS", size, size);
                return matrix;
            }
        } catch (Exception e) {
            log.error("Failed to fetch duration matrix from ORS: {}", e.getMessage());
            throw new RuntimeException("ORS API error: " + e.getMessage(), e);
        }

        throw new RuntimeException("Invalid response from ORS API");
    }
}
