package com.hostel.model;

import jakarta.persistence.*;

/**
 * Room entity - preserves all original logic from Room.java
 * capacity=2, isAvailable(), addStudent(), removeStudent()
 */
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_no")
    private int roomNo;

    @Column(nullable = false)
    private int capacity;       // same as original: 2

    @Column(name = "current_count")
    private int currentCount;   // same as original: 0

    public Room() {}

    // Same constructor as original
    public Room(int roomNo) {
        this.roomNo = roomNo;
        this.capacity = 2;
        this.currentCount = 0;
    }

    // Same logic as original
    public boolean isAvailable() {
        return currentCount < capacity;
    }

    // Same logic as original addStudent()
    public String addStudent() {
        if (currentCount < capacity) {
            currentCount++;
            return "Room Allocated Successfully!";
        } else {
            return "Room is already full.";
        }
    }

    // Same logic as original removeStudent()
    public void removeStudent() {
        if (currentCount > 0) {
            currentCount--;
        }
    }

    // Getters & Setters
    public int getRoomNo() { return roomNo; }
    public void setRoomNo(int roomNo) { this.roomNo = roomNo; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentCount() { return currentCount; }
    public void setCurrentCount(int currentCount) { this.currentCount = currentCount; }
}
