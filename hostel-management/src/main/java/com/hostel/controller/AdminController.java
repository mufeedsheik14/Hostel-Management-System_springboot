package com.hostel.controller;

import com.hostel.model.Student;
import com.hostel.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Admin REST Controller
 * Endpoints: login, addStudent, viewStudents, removeStudent
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private HostelService hostelService;

    /** POST /api/admin/login */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        hostelService.adminLogin(username, password);
        return ResponseEntity.ok(Map.of("message", "Admin Login Successful", "role", "admin"));
    }

    /** POST /api/admin/students - Add Student */
    @PostMapping("/students")
    public ResponseEntity<Map<String, Object>> addStudent(@RequestBody Map<String, Object> body) {
        String name     = (String) body.get("name");
        int    id       = Integer.parseInt(body.get("id").toString());
        String dept     = (String) body.get("department");
        int    year     = Integer.parseInt(body.get("year").toString());
        String username = (String) body.get("username");
        String password = (String) body.get("password");

        Student s = hostelService.addStudent(name, id, dept, year, username, password);
        return ResponseEntity.ok(Map.of("message", "Student Added Successfully!", "student", s));
    }

    /** GET /api/admin/students - View All Students */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> viewStudents() {
        return ResponseEntity.ok(hostelService.viewStudents());
    }

    /** DELETE /api/admin/students/{id} - Remove Student / Checkout */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, String>> removeStudent(@PathVariable int id) {
        String msg = hostelService.removeStudent(id);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    // ===================== NEW FEATURE 1 =====================

    /** GET /api/admin/students/{id} - Search Student by ID */
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> searchStudentById(@PathVariable int id) {
        Student s = hostelService.getStudentById(id);
        return ResponseEntity.ok(s);
    }
}
