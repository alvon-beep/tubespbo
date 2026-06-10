package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String date;
    private String delta; // e.g. "+120" or "-300"

    public WalletTransaction() {}

    public WalletTransaction(User user, String title, String date, String delta) {
        this.user = user;
        this.title = title;
        this.date = date;
        this.delta = delta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDelta() { return delta; }
    public void setDelta(String delta) { this.delta = delta; }
}
