package com.ecotukar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Embedded;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    private String username;
    private String password;
    private String name;
    private String email;
    private String avatar;
    private String address;
    private String joined;
    
    @Embedded
    private EWallet eWallet;

    public User() {}

    public User(String username, String password, String name, String email, String avatar, String address, String joined, int points, int ewalletBalance) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.joined = joined;
        this.eWallet = new EWallet(points, ewalletBalance);
    }

    public User(String username, String name, String email, String avatar, String address, String joined, int points, int ewalletBalance) {
        this.username = username;
        this.password = "123456"; // Default password
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.address = address;
        this.joined = joined;
        this.eWallet = new EWallet(points, ewalletBalance);
    }

    public User(String username, String name, String email, String avatar, String address, String joined, int points) {
        this(username, name, email, avatar, address, joined, points, 0);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract String getRole();

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getJoined() { return joined; }
    public void setJoined(String joined) { this.joined = joined; }

    public int getPoints() { return eWallet != null ? eWallet.getPoints() : 0; }
    public void setPoints(int points) { 
        if(eWallet == null) eWallet = new EWallet();
        eWallet.setPoints(points); 
    }

    public int getEwalletBalance() { return eWallet != null ? eWallet.getEwalletBalance() : 0; }
    public void setEwalletBalance(int ewalletBalance) { 
        if(eWallet == null) eWallet = new EWallet();
        eWallet.setEwalletBalance(ewalletBalance); 
    }

    public void addPoints(int amount) {
        if(eWallet == null) eWallet = new EWallet();
        this.eWallet.addCoins(amount);
    }

    public EWallet getEWallet() {
        if(eWallet == null) eWallet = new EWallet();
        return eWallet;
    }
}
