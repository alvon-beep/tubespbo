package com.ecotukar.model;

public class User {
    private String username;
    private String name;
    private String email;
    private String role; // CUSTOMER, COURIER, ADMIN
    private String avatar;
    private String address;
    private String joined;
    private int points;

    public User() {}

    public User(String username, String name, String email, String role, String avatar, String address, String joined, int points) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.address = address;
        this.joined = joined;
        this.points = points;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getJoined() { return joined; }
    public void setJoined(String joined) { this.joined = joined; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public void addPoints(int amount) {
        this.points += amount;
    }
}

// halo halo
