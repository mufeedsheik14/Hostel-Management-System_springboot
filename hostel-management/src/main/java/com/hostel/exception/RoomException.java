package com.hostel.exception;

/**
 * Thrown when room number is invalid or room is full
 */
public class RoomException extends RuntimeException {
    public RoomException(String message) {
        super(message);
    }
}
