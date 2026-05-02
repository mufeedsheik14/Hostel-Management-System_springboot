package com.hostel.exception;

/**
 * Thrown when login credentials are invalid (admin, warden, or student)
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String role) {
        super("Invalid " + role + " Credentials!");
    }
}
