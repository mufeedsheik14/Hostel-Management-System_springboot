package com.hostel.exception;

/**
 * Thrown when a student is not found by ID or credentials
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(int id) {
        super("Student Not Found with ID: " + id);
    }
    public StudentNotFoundException(String message) {
        super(message);
    }
}
