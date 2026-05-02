# 🏨 Hostel Management System

> Converted from plain Java (file-based) → **Spring Boot + Maven + SQLite + JPA + REST API + HTML**
> All original business logic is preserved exactly as-is.

---

## ✅ What Was Migrated

| Original | New |
|----------|-----|
| Plain Java (no framework) | Spring Boot 3.2 |
| File-based storage (.txt CSV) | SQLite via JPA/Hibernate |
| Manual build | Maven |
| Console UI | HTML Single-Page App |
| No error handling | Custom Exceptions + Global Handler |

---

## 📁 Project Structure

```
hostel-management/
├── pom.xml
└── src/main/
    ├── java/com/hostel/
    │   ├── HostelManagementApplication.java
    │   ├── model/
    │   │   ├── User.java           ← base class (same as original)
    │   │   ├── Student.java        ← JPA entity
    │   │   ├── Room.java           ← JPA entity
    │   │   └── Complaint.java      ← JPA entity
    │   ├── repository/
    │   │   ├── StudentRepository.java
    │   │   ├── RoomRepository.java
    │   │   └── ComplaintRepository.java
    │   ├── service/
    │   │   └── HostelService.java  ← all original logic
    │   ├── controller/
    │   │   ├── AdminController.java
    │   │   ├── WardenController.java
    │   │   └── StudentController.java
    │   └── exception/
    │       ├── GlobalExceptionHandler.java
    │       ├── StudentNotFoundException.java
    │       ├── RoomException.java
    │       ├── ComplaintNotFoundException.java
    │       └── InvalidCredentialsException.java
    └── resources/
        ├── application.properties
        └── static/
            └── index.html          ← full HTML UI
```

---

## ▶️ How to Run

### Prerequisites
- Java 17+
- Maven 3.6+

### Steps
```bash
cd hostel-management
mvn spring-boot:run
```

Open your browser: **http://localhost:8080**

> The SQLite database file `hostel.db` is created automatically in the project root.
> 20 rooms (capacity 2 each) are initialised on first run.

---

## 🔑 Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | `admin` | `admin123` |
| Warden | `warden` | `warden123` |
| Student | *(set by admin)* | *(set by admin)* |

---

## 📡 REST API Endpoints

### Admin
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/admin/login` | Admin login |
| POST | `/api/admin/students` | Add student |
| GET  | `/api/admin/students` | View all students |
| DELETE | `/api/admin/students/{id}` | Remove student |

### Warden
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/warden/login` | Warden login |
| POST | `/api/warden/allocate-room` | Allocate room to student |
| GET  | `/api/warden/complaints` | View all complaints |
| PUT  | `/api/warden/complaints/{id}/resolve` | Resolve complaint |
| GET  | `/api/warden/rooms` | View all rooms |

### Student
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/student/login` | Student login |
| GET  | `/api/student/{id}/profile` | View profile |
| POST | `/api/student/{id}/pay-fee` | Pay fee |
| POST | `/api/student/{id}/complaints` | Register complaint |
| GET  | `/api/student/{id}/complaints` | View complaint status |

---

## 💡 All Original Logic Preserved

- **20 rooms**, capacity **2** per room
- **Total fee = ₹50,000** — must pay full amount
- Fee already paid → "Fees already paid!"
- Amount < total fee → "NOT PAID — Please pay full fee!"
- Student with existing room → "Student already has a room!"
- Room full (2 students) → "Room is Full!"
- Remove student → frees room slot automatically
- Admin/Warden credentials hardcoded (same as original)
