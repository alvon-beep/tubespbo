package com.ecotukar.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class EWallet {
    private int points;
    private int ewalletBalance;

    public EWallet() {
        this.points = 0;
        this.ewalletBalance = 0;
    }

    public EWallet(int initialPoints, int initialBalance) {
        this.points = initialPoints;
        this.ewalletBalance = initialBalance;
    }

    // Encapsulated behavior
    public void addCoins(int amount) {
        if (amount > 0) {
            this.points += amount;
        }
    }

    public boolean convertCoinsToBalance(int coinsToConvert, int ratePerCoin) {
        if (coinsToConvert < 600 || this.points < coinsToConvert) {
            return false;
        }
        
        int convertedValue = coinsToConvert * ratePerCoin;
        this.points -= coinsToConvert;
        this.ewalletBalance += convertedValue;
        
        return true;
    }

    // Getters and Setters
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getEwalletBalance() {
        return ewalletBalance;
    }

    public void setEwalletBalance(int ewalletBalance) {
        this.ewalletBalance = ewalletBalance;
    }
}
