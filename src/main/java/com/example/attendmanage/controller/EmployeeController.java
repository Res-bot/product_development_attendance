package com.example.attendmanage.controller;

import com.example.attendmanage.dto.AttendanceDTO;
import com.example.attendmanage.dto.DashboardStatsDTO;
import com.example.attendmanage.dto.LeaveRequestDTO;
import com.example.attendmanage.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.attendmanage.entity.User; 

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE', 'TEACHER', 'ADMIN')") 
public class EmployeeController {

    private final EmployeeService employeeService;

    private Long getCurrentUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
    

    @PostMapping("/checkin")
    public ResponseEntity<AttendanceDTO> checkIn(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        AttendanceDTO record = employeeService.checkIn(userId, LocalDateTime.now(), "Self Check-in");
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PutMapping("/checkout")
    public ResponseEntity<AttendanceDTO> checkOut(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        AttendanceDTO record = employeeService.checkOut(userId, LocalDateTime.now());
        return ResponseEntity.ok(record);
    }

    
    @PostMapping("/leave/apply")
    public ResponseEntity<LeaveRequestDTO> applyForLeave(
            Authentication authentication,
            @Valid @RequestBody LeaveRequestDTO request) {
        
        Long userId = getCurrentUserId(authentication);
        LeaveRequestDTO newRequest = employeeService.applyForLeave(userId, request);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }


    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getPersonalDashboardStats(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        DashboardStatsDTO stats = employeeService.getPersonalStats(userId);
        return ResponseEntity.ok(stats);
    }
}

