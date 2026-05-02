package com.hostel.exception;

/**
 * Thrown when a complaint ID is not found
 */
public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(int id) {
        super("Complaint Not Found with ID: " + id);
    }
}
