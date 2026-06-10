package com.ecotukar.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COURIER")
public class CourierUser extends User {

    public CourierUser() {}

    public CourierUser(String username, String password, String name, String email, String avatar, String address, String joined, int points, int ewalletBalance) {
        super(username, password, name, email, avatar, address, joined, points, ewalletBalance);
    }

    public CourierUser(String username, String name, String email, String avatar, String address, String joined, int points, int ewalletBalance) {
        super(username, name, email, avatar, address, joined, points, ewalletBalance);
    }

    public CourierUser(String username, String name, String email, String avatar, String address, String joined, int points) {
        super(username, name, email, avatar, address, joined, points);
    }

    @Override
    public String getRole() {
        return "COURIER";
    }
}
