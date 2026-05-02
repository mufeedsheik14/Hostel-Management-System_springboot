package com.hostel.service;

import com.hostel.exception.*;
import com.hostel.model.*;
import com.hostel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * HostelService - all original logic from HostelService.java preserved.
 * File I/O replaced with JPA/SQLite. Same method names and behaviour.
 */
@Service
public class HostelService implements CommandLineRunner {

    @Autowired private StudentRepository studentRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private ComplaintRepository complaintRepository;

    @Value("${app.rooms.count:20}")
    private int roomsCount;

    @Value("${app.rooms.capacity:2}")
    private int roomCapacity;

    @Value("${app.admin.username}")
    private String adminUser;

    @Value("${app.admin.password}")
    private String adminPass;

    @Value("${app.warden.username}")
    private String wardenUser;

    @Value("${app.warden.password}")
    private String wardenPass;

    // ---- Initialise 20 rooms on startup (same as original constructor) ----
    @Override
    public void run(String... args) {
        if (roomRepository.count() == 0) {
            for (int i = 1; i <= roomsCount; i++) {
                Room room = new Room(i);
                room.setCapacity(roomCapacity);
                roomRepository.save(room);
            }
            System.out.println("20 Rooms initialised in DB.");
        }
    }

    // ===================== AUTH =====================

    /** Admin login - same as original ADMIN_USER / ADMIN_PASS check */
    public boolean adminLogin(String username, String password) {
        if (adminUser.equals(username) && adminPass.equals(password)) return true;
        throw new InvalidCredentialsException("Admin");
    }

    /** Warden login - same as original WARDEN_USER / WARDEN_PASS check */
    public boolean wardenLogin(String username, String password) {
        if (wardenUser.equals(username) && wardenPass.equals(password)) return true;
        throw new InvalidCredentialsException("Warden");
    }

    /**
     * Student login - same as original studentLogin().
     * Returns student ID on success, throws exception on failure.
     */
    public int studentLogin(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username, password)
                .map(Student::getStudentId)
                .orElseThrow(() -> new InvalidCredentialsException("Student"));
    }

    // ===================== ADMIN METHODS =====================

    /**
     * addStudent - same as original addStudent().
     * Creates student with roomNo=0, feePaid=0, totalFee=50000.
     */
    @Transactional
    public Student addStudent(String name, int id, String dept, int year, String username, String password) {
        if (studentRepository.existsById(id)) {
            throw new IllegalArgumentException("Student ID " + id + " already exists!");
        }
        if (studentRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' already taken!");
        }
        Student s = new Student(name, id, dept, year, username, password);
        return studentRepository.save(s);
    }

    /** viewStudents - same as original viewStudents() */
    public List<Student> viewStudents() {
        return studentRepository.findAll();
    }

    /**
     * removeStudent - same as original removeStudent().
     * Frees the room if student had one allocated.
     */
    @Transactional
    public String removeStudent(int studentId) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        int roomNo = s.getRoom();
        if (roomNo != 0) {
            Room room = roomRepository.findById(roomNo).orElse(null);
            if (room != null) {
                room.removeStudent();
                roomRepository.save(room);
            }
        }

        studentRepository.delete(s);

        String msg = "Student Removed Successfully!";
        if (roomNo != 0) msg += " Room " + roomNo + " occupancy reduced.";
        return msg;
    }

    // ===================== WARDEN METHODS =====================

    /**
     * allocateRoom - same as original allocateRoom().
     * Validates room range (1-20), checks availability, checks student already has room.
     */
    @Transactional
    public String allocateRoom(int studentId, int roomNo) {
        if (roomNo < 1 || roomNo > roomsCount) {
            throw new RoomException("Invalid Room Number!");
        }

        Room room = roomRepository.findById(roomNo)
                .orElseThrow(() -> new RoomException("Room " + roomNo + " not found!"));

        if (!room.isAvailable()) {
            throw new RoomException("Room is Full!");
        }

        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        if (s.getRoom() != 0) {
            throw new RoomException("Student already has a room!");
        }

        s.setRoom(roomNo);
        room.addStudent();

        studentRepository.save(s);
        roomRepository.save(room);

        return "Room Allocated Successfully!";
    }

    /** viewComplaints - same as original viewComplaints() */
    public List<Complaint> viewComplaints() {
        return complaintRepository.findAll();
    }

    /**
     * resolveComplaint - same as original resolveComplaint().
     * Marks complaint as resolved.
     */
    @Transactional
    public String resolveComplaint(int complaintId) {
        Complaint c = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException(complaintId));
        c.markResolved();
        complaintRepository.save(c);
        return "Complaint Resolved!";
    }

    /** viewAllRooms - same as original viewAllRooms() */
    public List<Room> viewAllRooms() {
        return roomRepository.findAll();
    }

    // ===================== STUDENT METHODS =====================

    /** viewProfile - same as original viewProfile() */
    public Student viewProfile(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    /**
     * payFee - same as original payFee() logic.
     * Already paid -> error. Amount < totalFee -> NOT PAID. Else -> PAID.
     */
    @Transactional
    public String payFee(int studentId, double amount) {
        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        String result = s.payFee(amount);
        studentRepository.save(s);
        return result;
    }

    /**
     * registerComplaint - same as original registerComplaint().
     * Auto-incremented ID handled by JPA.
     */
    @Transactional
    public Complaint registerComplaint(int studentId, String description) {
        // Verify student exists (same check in original)
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
        Complaint c = new Complaint(studentId, description);
        complaintRepository.save(c);
        return c;
    }

    /** viewComplaintStatus - same as original viewComplaintStatus() */
    public List<Complaint> viewComplaintStatus(int studentId) {
        return complaintRepository.findByStudentId(studentId);
    }

    // ===================== NEW FEATURES =====================

    /**
     * FEATURE 1 - Search Student by ID (Admin)
     * Returns single student by ID. Throws StudentNotFoundException if not found.
     */
    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    /**
     * FEATURE 2 - Get Students in a Room (Warden - room click)
     * Returns list of students whose roomNo matches given room number.
     */
    public List<Student> getStudentsInRoom(int roomNo) {
        if (roomNo < 1 || roomNo > roomsCount) {
            throw new RoomException("Invalid Room Number: " + roomNo);
        }
        if (!roomRepository.existsById(roomNo)) {
            throw new RoomException("Room " + roomNo + " not found!");
        }
        return studentRepository.findByRoomNo(roomNo);
    }

    /**
     * FEATURE 3 - Change Room (Warden)
     * Conditions:
     *  - Student must exist
     *  - New room must exist and not be full
     *  - Cannot assign the same room again
     * Frees old room slot, allocates new room slot.
     */
    @Transactional
    public String changeRoom(int studentId, int newRoomNo) {
        if (newRoomNo < 1 || newRoomNo > roomsCount) {
            throw new RoomException("Invalid Room Number: " + newRoomNo);
        }

        Student s = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        int currentRoomNo = s.getRoom();

        if (currentRoomNo == newRoomNo) {
            throw new RoomException("Student is already in Room " + newRoomNo + "!");
        }

        Room newRoom = roomRepository.findById(newRoomNo)
                .orElseThrow(() -> new RoomException("Room " + newRoomNo + " not found!"));

        if (!newRoom.isAvailable()) {
            throw new RoomException("Room " + newRoomNo + " is Full!");
        }

        // Free old room if student had one
        if (currentRoomNo != 0) {
            Room oldRoom = roomRepository.findById(currentRoomNo).orElse(null);
            if (oldRoom != null) {
                oldRoom.removeStudent();
                roomRepository.save(oldRoom);
            }
        }

        // Assign new room
        s.setRoom(newRoomNo);
        newRoom.addStudent();

        studentRepository.save(s);
        roomRepository.save(newRoom);

        return "Room changed successfully! Student " + studentId +
               " moved from Room " + (currentRoomNo == 0 ? "None" : currentRoomNo) +
               " to Room " + newRoomNo + ".";
    }
}
