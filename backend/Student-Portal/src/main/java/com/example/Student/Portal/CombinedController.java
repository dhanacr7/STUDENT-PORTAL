package com.example.Student.Portal;

import com.example.Student.Portal.Admin.Admin;
import com.example.Student.Portal.Admin.AdminService;
import com.example.Student.Portal.Student.Student;
import com.example.Student.Portal.Student.StudentService;
import com.example.Student.Portal.Teacher.Teacher;
import com.example.Student.Portal.Teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CombinedController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Show signup form
    @GetMapping("/signup.html")
    public String showSignupForm() {
        return "signup.html";  // ✅ Show the actual signup form
    }


    // Process signup
    @PostMapping("/register")
    public String registerUser(@RequestParam String role,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam(required = false) String department,
                               @RequestParam String userId,
                               @RequestParam String password,
                               Model model) {

        String hashedPassword = passwordEncoder.encode(password);

        switch (role.toLowerCase()) {
            case "student.html":
                if (studentService.getByStudentId(userId) != null) {
                    model.addAttribute("error", "Student ID already exists");
                    return "signup.html";
                }
                Student s = new Student(name, email, department, userId, hashedPassword);
                studentService.registerStudent(s);
                break;

            case "teacher.html":
                if (teacherService.getByTeacherId(userId) != null) {
                    model.addAttribute("error", "Teacher ID already exists");
                    return "signup.html";
                }
                Teacher t = new Teacher(name, email, department, userId, hashedPassword);
                teacherService.registerTeacher(t);
                break;

            case "admin.html":
                if (adminService.getAdminById(userId) != null) {
                    model.addAttribute("error", "Admin ID already exists");
                    return "signup.html";
                }
                Admin a = new Admin(name, email, userId, hashedPassword);
                adminService.registerAdmin(a);
                break;

            default:
                model.addAttribute("error", "Invalid role");
                return "signup.html";
        }

        return "signupsuccess.html"; // directly renders signupsuccess.html
    }

    // Show login page
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "login.html";
//    }

    // Handle student login
    @PostMapping("/login/student")
    public String loginStudent(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        Student s = studentService.getByStudentId(username);
        if (s != null && passwordEncoder.matches(password, s.getPassword())) {
            model.addAttribute("student", s);
            return "student.html";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // Handle teacher login
    @PostMapping("/login/teacher")
    public String loginTeacher(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        Teacher t = teacherService.getByTeacherId(username);
        if (t != null && passwordEncoder.matches(password, t.getPassword())) {
            model.addAttribute("teacher", t);
            return "teacher.html";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    // Handle admin login
    @PostMapping("/login/admin")
    public String loginAdmin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        Admin a = adminService.getAdminById(username);
        if (a != null && passwordEncoder.matches(password, a.getPassword())) {
            model.addAttribute("admin", a);
            return "admin.html";
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }
}
