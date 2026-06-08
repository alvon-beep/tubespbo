package com.ecotukar.exception;

public class InvalidPickupRequestException extends RuntimeException {
    public InvalidPickupRequestException(String message) {
        super(message);
    }
}
