package com.hostel.model;

import jakarta.persistence.*;

/**
 * Student entity - preserves all original fields and logic from Student.java
 * Extends User (same as original inheritance)
 */
@Entity
@Table(name = "students")
public class Student extends User {

    @Id
    @Column(name = "student_id", unique = true, nullable = false)
    private int studentId;   // maps to original 'id'

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private int year;

    @Column(name = "room_no")
    private int roomNo;         // 0 = no room (same as original default)

    @Column(name = "fee_paid")
    private double feePaid;     // 0 = not paid (same as original)

    @Column(name = "total_fee")
    private double totalFee = 50000;  // same as original

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Student() {}

    // Same constructor as original
    public Student(String name, int id, String department, int year, String username, String password) {
        super(name, id);
        this.studentId = id;
        this.department = department;
        this.year = year;
        this.roomNo = 0;
        this.feePaid = 0;
        this.totalFee = 50000;
        this.username = username;
        this.password = password;
    }

    // ---- Same logic as original payFee() ----
    public String payFee(double amount) {
        if (feePaid > 0) {
            return "Fees already paid!";
        }
        if (amount < totalFee) {
            return "Entered Amount: " + amount + " | Status: NOT PAID | Please pay full fee!";
        }
        feePaid = amount;
        return "Entered Amount: " + amount + " | Status: PAID | Fee Paid Successfully!";
    }

    // ---- Same logic as original showFeeDetails() ----
    public String getFeeStatus() {
        if (feePaid == 0) return "NOT PAID";
        return "PAID";
    }

    // Getters & Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
        this.id = studentId;
    }

    @Override
    public int getId() { return studentId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    // keep original method name
    public int getRoom() { return roomNo; }
    public void setRoom(int roomNo) { this.roomNo = roomNo; }

    public double getFeePaid() { return feePaid; }
    public void setFeePaid(double feePaid) { this.feePaid = feePaid; }

    public double getTotalFee() { return totalFee; }
    public void setTotalFee(double totalFee) { this.totalFee = totalFee; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
