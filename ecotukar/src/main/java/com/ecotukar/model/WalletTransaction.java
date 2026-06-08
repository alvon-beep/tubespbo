package com.ecotukar.model;

public class WalletTransaction {
    private String title;
    private String date;
    private String delta; // e.g. "+120" or "-300"

    public WalletTransaction() {}

    public WalletTransaction(String title, String date, String delta) {
        this.title = title;
        this.date = date;
        this.delta = delta;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDelta() { return delta; }
    public void setDelta(String delta) { this.delta = delta; }
}
