package com.example.Student.Portal.Teacher;

import com.example.Student.Portal.Marks.Marks;
import com.example.Student.Portal.Marks.MarksService;
import com.example.Student.Portal.Attendance.Attendance;
import com.example.Student.Portal.Attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MarksService marksService;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        try {
            System.out.println("Teacher login - Raw request received: " + request);
            
            String email = request.get("email");
            String password = request.get("password");
            
            if (email == null || password == null) {
                System.out.println("Missing email or password in teacher login request");
                return ResponseEntity.badRequest().body("Email and password are required");
            }
            
            System.out.println("Teacher login attempt - Email: " + email + ", Password: " + password);
            
            // Find teacher by email first
            Teacher teacher = teacherService.findByEmail(email);
            if (teacher == null) {
                System.out.println("Teacher not found with email: " + email);
                return ResponseEntity.badRequest().body("Teacher not found with this email");
            }
            
            System.out.println("Teacher found: " + teacher.getName());
            System.out.println("Stored password: " + teacher.getPassword());
            
            // For existing plain text passwords, do direct comparison
            // For new BCrypt passwords, use BCrypt verification
            boolean isValidPassword = false;
            
            if (teacher.getPassword().startsWith("$2a$") || teacher.getPassword().startsWith("$2b$")) {
                // This is a BCrypt password
                isValidPassword = teacherService.verifyPassword(password, teacher.getPassword());
                System.out.println("BCrypt verification result: " + isValidPassword);
            } else {
                // This is a plain text password (existing data)
                isValidPassword = password.equals(teacher.getPassword());
                System.out.println("Plain text comparison result: " + isValidPassword);
            }
            
            if (isValidPassword) {
                System.out.println("Teacher login successful for: " + teacher.getName());
                return ResponseEntity.ok("Login successful! Welcome, " + teacher.getName());
            } else {
                System.out.println("Teacher password mismatch");
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } catch (Exception e) {
            System.out.println("Teacher login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/marks")
    public ResponseEntity<?> submitMarks(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            String studentName = request.get("studentName").toString();
            String subject = request.get("subject").toString();
            Integer marks = Integer.valueOf(request.get("marks").toString());
            
            Marks marksEntity = new Marks(studentId, studentName, subject, marks);
            Marks saved = marksService.saveMarks(marksEntity);
            
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error submitting marks: " + e.getMessage());
        }
    }

    @PostMapping("/attendance")
    public ResponseEntity<?> submitAttendance(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.valueOf(request.get("studentId").toString());
            String studentName = request.get("studentName").toString();
            Double attendancePercent = Double.valueOf(request.get("attendancePercent").toString());
            
            Attendance attendance = new Attendance(studentId, studentName, attendancePercent);
            Attendance saved = attendanceService.saveAttendance(attendance);
            
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error submitting attendance: " + e.getMessage());
        }
    }
}