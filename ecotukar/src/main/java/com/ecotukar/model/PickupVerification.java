package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pickup_verifications")
public class PickupVerification {
    @Id
    private String verificationId;

    @OneToOne
    @JoinColumn(name = "pickup_request_id")
    private PickupRequest pickupRequest; // OOP Relationship: Association/Aggregation
    private boolean dataValid;
    private String physicalCondition;
    private String verificationTime;

    public PickupVerification() {}

    public PickupVerification(String verificationId, PickupRequest pickupRequest, boolean dataValid, String physicalCondition, String verificationTime) {
        this.verificationId = verificationId;
        this.pickupRequest = pickupRequest;
        this.dataValid = dataValid;
        this.physicalCondition = physicalCondition;
        this.verificationTime = verificationTime;
    }

    public String getVerificationId() { return verificationId; }
    public void setVerificationId(String verificationId) { this.verificationId = verificationId; }

    public PickupRequest getPickupRequest() { return pickupRequest; }
    public void setPickupRequest(PickupRequest pickupRequest) { this.pickupRequest = pickupRequest; }

    public boolean isDataValid() { return dataValid; }
    public void setDataValid(boolean dataValid) { this.dataValid = dataValid; }

    public String getPhysicalCondition() { return physicalCondition; }
    public void setPhysicalCondition(String physicalCondition) { this.physicalCondition = physicalCondition; }

    public String getVerificationTime() { return verificationTime; }
    public void setVerificationTime(String verificationTime) { this.verificationTime = verificationTime; }
}
