package com.ecotukar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {
    private static final Logger log = LoggerFactory.getLogger(GeocodingService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ecotukar.hub.lat:-6.9175}")
    private double defaultLat;

    @Value("${ecotukar.hub.lng:107.6191}")
    private double defaultLng;

    /**
     * Geocode an address into [latitude, longitude].
     * If the address is invalid or the request fails, returns default hub
     * coordinates.
     */
    public double[] geocode(String address) {
        if (address == null || address.trim().isEmpty()) {
            return new double[] { defaultLat, defaultLng };
        }

        // If it's a seeded mock place that isn't search-friendly, or we're offline,
        // return default
        if (address.equalsIgnoreCase("padang")) {
            return new double[] { -6.9250, 107.5950 }; // Padang is far, place it in Bandung for routing demo
        }
        if (address.equalsIgnoreCase("Hub Bandung")) {
            return new double[] { defaultLat, defaultLng };
        }
        if (address.equalsIgnoreCase("Hub Jakarta")) {
            return new double[] { -6.9380, 107.6220 }; // Demo coordinates in Bandung
        }

        try {
            // 1. Use .build().toUri() instead of .toUriString()
            java.net.URI uri = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                    .queryParam("q", address)
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "EcoTukar-App/1.0 (contact@ecotukar.id)");
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            // 2. Pass the 'uri' object directly into the exchange method
            ResponseEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);
            List<?> body = response.getBody();

            if (body != null && !body.isEmpty()) {
                Map<?, ?> first = (Map<?, ?>) body.get(0);
                double lat = Double.parseDouble(first.get("lat").toString());
                double lon = Double.parseDouble(first.get("lon").toString());
                log.info("Geocoded address '{}' to [{}, {}]", address, lat, lon);
                return new double[] { lat, lon };
            }
        } catch (Exception e) {
            log.error("Failed to geocode address '{}': {}", address, e.getMessage());
        }

        log.warn("Fallback to default coords [{}, {}] for address '{}'", defaultLat, defaultLng, address);
        return new double[] { defaultLat, defaultLng };
    }
}
