package com.hostel.model;

import jakarta.persistence.*;

/**
 * Complaint entity - preserves all original logic from Complaint.java
 * complaintId, studentId, description, resolved(boolean)
 */
@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private int complaintId;

    @Column(name = "student_id", nullable = false)
    private int studentId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean resolved;  // same as original: false by default

    public Complaint() {}

    // Same constructor as original
    public Complaint(int studentId, String description) {
        this.studentId = studentId;
        this.description = description;
        this.resolved = false;
    }

    // Same method as original markResolved()
    public void markResolved() {
        this.resolved = true;
    }

    // Same status string as original display()
    public String getStatus() {
        return resolved ? "Resolved" : "Pending";
    }

    // Getters & Setters
    public int getComplaintId() { return complaintId; }
    public void setComplaintId(int complaintId) { this.complaintId = complaintId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
}
