package com.hostel.controller;

import com.hostel.model.Complaint;
import com.hostel.model.Room;
import com.hostel.model.Student;
import com.hostel.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Warden REST Controller
 * Endpoints: login, allocateRoom, viewComplaints, resolveComplaint, viewAllRooms
 */
@RestController
@RequestMapping("/api/warden")
@CrossOrigin(origins = "*")
public class WardenController {

    @Autowired
    private HostelService hostelService;

    /** POST /api/warden/login */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        hostelService.wardenLogin(body.get("username"), body.get("password"));
        return ResponseEntity.ok(Map.of("message", "Warden Login Successful", "role", "warden"));
    }

    /** POST /api/warden/allocate-room */
    @PostMapping("/allocate-room")
    public ResponseEntity<Map<String, String>> allocateRoom(@RequestBody Map<String, Object> body) {
        int studentId = Integer.parseInt(body.get("studentId").toString());
        int roomNo    = Integer.parseInt(body.get("roomNo").toString());
        String msg = hostelService.allocateRoom(studentId, roomNo);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    /** GET /api/warden/complaints */
    @GetMapping("/complaints")
    public ResponseEntity<List<Complaint>> viewComplaints() {
        return ResponseEntity.ok(hostelService.viewComplaints());
    }

    /** PUT /api/warden/complaints/{id}/resolve */
    @PutMapping("/complaints/{id}/resolve")
    public ResponseEntity<Map<String, String>> resolveComplaint(@PathVariable int id) {
        String msg = hostelService.resolveComplaint(id);
        return ResponseEntity.ok(Map.of("message", msg));
    }

    /** GET /api/warden/rooms */
    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> viewAllRooms() {
        return ResponseEntity.ok(hostelService.viewAllRooms());
    }

    // ===================== NEW FEATURE 2 =====================

    /** GET /api/warden/rooms/{roomNo}/students - Get students in a room */
    @GetMapping("/rooms/{roomNo}/students")
    public ResponseEntity<List<Student>> getStudentsInRoom(@PathVariable int roomNo) {
        List<Student> students = hostelService.getStudentsInRoom(roomNo);
        return ResponseEntity.ok(students);
    }

    // ===================== NEW FEATURE 3 =====================

    /** PUT /api/warden/change-room - Change student's room */
    @PutMapping("/change-room")
    public ResponseEntity<Map<String, String>> changeRoom(@RequestBody Map<String, Object> body) {
        int studentId = Integer.parseInt(body.get("studentId").toString());
        int newRoomNo = Integer.parseInt(body.get("newRoomNo").toString());
        String msg = hostelService.changeRoom(studentId, newRoomNo);
        return ResponseEntity.ok(Map.of("message", msg));
    }
}
