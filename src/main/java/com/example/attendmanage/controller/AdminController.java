package com.example.attendmanage.controller;

import com.example.attendmanage.dto.CourseDTO;
import com.example.attendmanage.dto.DepartmentDTO;
import com.example.attendmanage.dto.UserDTO;
import com.example.attendmanage.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {

    private final AdminService adminService;

    
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
    
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return new ResponseEntity<>(adminService.createDepartment(dto), HttpStatus.CREATED);
    }
    
    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO dto) {
        return new ResponseEntity<>(adminService.createCourse(dto), HttpStatus.CREATED);
    }
    
}
