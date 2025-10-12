package com.ams.attendance.controller;

import com.ams.attendance.dto.CourseDTO;
import com.ams.attendance.dto.DepartmentDTO;
import com.ams.attendance.dto.UserDTO;
import com.ams.attendance.enums.UserRole;
import com.ams.attendance.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // Base authorization for the entire controller
public class AdminController {

    private  AdminService adminService;

    // --- User Management (Feature 2) ---

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(required = false) UserRole role) {
        if (role != null) {
            return ResponseEntity.ok(adminService.findUsersByRole(role));
        }
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO createdUser = adminService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDto) {
        UserDTO updatedUser = adminService.updateUser(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    // --- System Administration (Feature 8) ---
    
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return new ResponseEntity<>(adminService.createDepartment(dto), HttpStatus.CREATED);
    }
    
    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO dto) {
        return new ResponseEntity<>(adminService.createCourse(dto), HttpStatus.CREATED);
    }
    
    // --- Reports and Bulk Upload (Feature 2 & 4) ---
    
    @PostMapping("/users/bulk-upload")
    public ResponseEntity<String> uploadBulkUsers(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File must not be empty.");
        }
        try {
            String status = adminService.uploadBulkUsers(file.getBytes());
            return ResponseEntity.ok(status);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read file.");
        }
    }
    
    @GetMapping("/reports/attendance")
    public ResponseEntity<String> generateSystemReport(@RequestParam String period) {
        String reportStatus = adminService.generateFullAttendanceReport(period);
        return ResponseEntity.ok(reportStatus);
    }
}

