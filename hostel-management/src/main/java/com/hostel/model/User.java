package com.hostel.model;

import jakarta.persistence.MappedSuperclass;

/**
 * Base User class - same as original User.java
 */
@MappedSuperclass
public abstract class User {

    protected String name;
    protected int id;

    public User() {}

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }

    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
}
