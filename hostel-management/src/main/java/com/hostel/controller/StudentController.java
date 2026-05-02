package com.hostel.controller;

import com.hostel.model.Complaint;
import com.hostel.model.Student;
import com.hostel.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Student REST Controller
 * Endpoints: login, viewProfile, payFee, registerComplaint, viewComplaintStatus
 */
@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private HostelService hostelService;

    /** POST /api/student/login */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        int id = hostelService.studentLogin(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of("message", "Student Login Successful", "studentId", id, "role", "student"));
    }

    /** GET /api/student/{id}/profile */
    @GetMapping("/{id}/profile")
    public ResponseEntity<Student> viewProfile(@PathVariable int id) {
        return ResponseEntity.ok(hostelService.viewProfile(id));
    }

    /** POST /api/student/{id}/pay-fee */
    @PostMapping("/{id}/pay-fee")
    public ResponseEntity<Map<String, String>> payFee(
            @PathVariable int id,
            @RequestBody Map<String, Object> body) {
        double amount = Double.parseDouble(body.get("amount").toString());
        String result = hostelService.payFee(id, amount);
        return ResponseEntity.ok(Map.of("message", result));
    }

    /** POST /api/student/{id}/complaints */
    @PostMapping("/{id}/complaints")
    public ResponseEntity<Map<String, Object>> registerComplaint(
            @PathVariable int id,
            @RequestBody Map<String, String> body) {
        Complaint c = hostelService.registerComplaint(id, body.get("description"));
        return ResponseEntity.ok(Map.of("message", "Complaint Registered!", "complaint", c));
    }

    /** GET /api/student/{id}/complaints */
    @GetMapping("/{id}/complaints")
    public ResponseEntity<List<Complaint>> viewComplaintStatus(@PathVariable int id) {
        return ResponseEntity.ok(hostelService.viewComplaintStatus(id));
    }
}
