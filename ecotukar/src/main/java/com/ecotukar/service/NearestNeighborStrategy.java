package com.ecotukar.service;

import com.ecotukar.model.PickupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Nearest Neighbor implementation of RoutingStrategy.
 * Solves the Traveling Salesperson Problem using a greedy nearest-neighbor approach.
 * Uses OpenRouteService travel time matrix, falling back to Haversine straight-line distance if ORS is unavailable.
 */
@Service
public class NearestNeighborStrategy implements RoutingStrategy {
    private static final Logger log = LoggerFactory.getLogger(NearestNeighborStrategy.class);
    private final OrsMatrixClient orsMatrixClient;

    public NearestNeighborStrategy(OrsMatrixClient orsMatrixClient) {
        this.orsMatrixClient = orsMatrixClient;
    }

    @Override
    public List<PickupRequest> optimizeRoute(double hubLat, double hubLng, List<PickupRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return new ArrayList<>();
        }

        // Clean up requests - assign default coordinates if null
        for (PickupRequest req : requests) {
            if (req.getLat() == null || req.getLng() == null) {
                req.setLat(hubLat);
                req.setLng(hubLng);
            }
        }

        int n = requests.size();
        List<double[]> coordinates = new ArrayList<>();
        // Add hub at index 0
        coordinates.add(new double[]{hubLat, hubLng});
        // Add pickups at index 1 to n
        for (PickupRequest req : requests) {
            coordinates.add(new double[]{req.getLat(), req.getLng()});
        }

        double[][] matrix = null;

        // Try OpenRouteService
        try {
            matrix = orsMatrixClient.getDurationMatrix(coordinates);
            log.info("Using OpenRouteService Matrix API for route optimization.");
        } catch (Exception e) {
            log.warn("ORS Matrix API failed or unavailable ({}). Falling back to Haversine straight-line distance.", e.getMessage());
        }

        // Fallback to Haversine if ORS failed or key was missing
        if (matrix == null) {
            int totalPoints = n + 1;
            matrix = new double[totalPoints][totalPoints];
            for (int i = 0; i < totalPoints; i++) {
                double[] c1 = coordinates.get(i);
                for (int j = 0; j < totalPoints; j++) {
                    double[] c2 = coordinates.get(j);
                    matrix[i][j] = haversine(c1[0], c1[1], c2[0], c2[1]);
                }
            }
            log.info("Successfully computed Haversine distance matrix.");
        }

        // Solve TSP using Nearest Neighbor
        List<PickupRequest> sortedRequests = new ArrayList<>();
        boolean[] visited = new boolean[n + 1]; // index 0 is Hub (already visited/started)
        int current = 0; // start at hub

        for (int step = 0; step < n; step++) {
            int nextIndex = -1;
            double minCost = Double.MAX_VALUE;

            for (int j = 1; j <= n; j++) {
                if (!visited[j]) {
                    double cost = matrix[current][j];
                    if (cost < minCost) {
                        minCost = cost;
                        nextIndex = j;
                    }
                }
            }

            if (nextIndex != -1) {
                visited[nextIndex] = true;
                sortedRequests.add(requests.get(nextIndex - 1));
                current = nextIndex;
            }
        }

        return sortedRequests;
    }

    /**
     * Compute Haversine distance in kilometers between two points.
     */
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
