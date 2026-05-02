package com.hostel.repository;

import com.hostel.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Used by studentLogin - same logic as original
    Optional<Student> findByUsernameAndPassword(String username, String password);

    // Check if username already taken
    boolean existsByUsername(String username);

    // NEW: Get all students in a specific room (Feature 2 - Room click)
    List<Student> findByRoomNo(int roomNo);
}
