package com.ecotukar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pickup_requests")
public class PickupRequest {
    @Id
    private String id; // REQ-XXXX
    private String username;
    private String customerName;
    private String address;
    private String wasteType;
    private double estimatedWeight;
    private double actualWeight;
    private String date;
    private String note;
    private String courier; // Assigned courier name or "Belum"
    private String status; // PENDING, ASSIGNED, ON_ROUTE, COMPLETED, CANCELLED
    private String time; // Simulated pickup target time

    public PickupRequest() {}

    public PickupRequest(String id, String username, String customerName, String address, String wasteType, 
                         double estimatedWeight, String date, String note, String courier, String status, String time) {
        this.id = id;
        this.username = username;
        this.customerName = customerName;
        this.address = address;
        this.wasteType = wasteType;
        this.estimatedWeight = estimatedWeight;
        this.actualWeight = 0;
        this.date = date;
        this.note = note;
        this.courier = courier;
        this.status = status;
        this.time = time;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getWasteType() { return wasteType; }
    public void setWasteType(String wasteType) { this.wasteType = wasteType; }

    public double getEstimatedWeight() { return estimatedWeight; }
    public void setEstimatedWeight(double estimatedWeight) { this.estimatedWeight = estimatedWeight; }

    public double getActualWeight() { return actualWeight; }
    public void setActualWeight(double actualWeight) { this.actualWeight = actualWeight; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getCourier() { return courier; }
    public void setCourier(String courier) { this.courier = courier; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
