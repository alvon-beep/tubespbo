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

    @Value("${google.api.key:}")
    private String googleApiKey;

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
            if (googleApiKey == null || googleApiKey.trim().isEmpty()) {
                throw new IllegalStateException("Google API key is missing");
            }

            java.net.URI uri = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                    .queryParam("address", address)
                    .queryParam("key", googleApiKey)
                    .build()
                    .toUri();

            // Google Maps handles JSON natively, standard RestTemplate GET works perfectly
            ResponseEntity<Map> response = restTemplate.getForEntity(uri, Map.class);
            Map<?, ?> body = response.getBody();

            if (body != null && "OK".equals(body.get("status"))) {
                List<?> results = (List<?>) body.get("results");
                if (!results.isEmpty()) {
                    Map<?, ?> firstResult = (Map<?, ?>) results.get(0);
                    Map<?, ?> geometry = (Map<?, ?>) firstResult.get("geometry");
                    Map<?, ?> location = (Map<?, ?>) geometry.get("location");

                    double lat = Double.parseDouble(location.get("lat").toString());
                    double lon = Double.parseDouble(location.get("lng").toString()); // Google uses 'lng' instead of
                                                                                     // 'lon'

                    log.info("Google Geocoded address '{}' to [{}, {}]", address, lat, lon);
                    return new double[] { lat, lon };
                }
            } else {
                log.warn("Google API returned status: {}", body != null ? body.get("status") : "NULL");
            }
        } catch (Exception e) {
            log.error("Failed to Google geocode address '{}': {}", address, e.getMessage());
        }

        log.warn("Fallback to default coords [{}, {}] for address '{}'", defaultLat, defaultLng, address);
        return new double[] { defaultLat, defaultLng };
    }
}
