package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;

    private String name;
    private String email;
    private String role; // CUSTOMER, COURIER, ADMIN
    private String avatar;
    private String address;
    private String joined;
    private int points;

    public User() {}

    public User(String username, String password, String name, String email, String role, String avatar, String address, String joined, int points) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
        this.address = address;
        this.joined = joined;
        this.points = points;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

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
