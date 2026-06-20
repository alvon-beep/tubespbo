package com.ecotukar.service;

import com.ecotukar.model.PickupRequest;
import java.util.List;

/**
 * Interface defining the strategy for route optimization.
 * Part of the Strategy Design Pattern.
 */
public interface RoutingStrategy {
    /**
     * Optimizes the pickup requests order starting from the Hub.
     *
     * @param hubLat Latitude of the starting Hub.
     * @param hubLng Longitude of the starting Hub.
     * @param requests The list of pickup requests to sort.
     * @return A sorted list of pickup requests representing the optimized route.
     */
    List<PickupRequest> optimizeRoute(double hubLat, double hubLng, List<PickupRequest> requests);
}
