package com.ams.attendance.controller;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.ams.attendance.entity.User; // Required for casting principal

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')") // Base authorization
public class StudentController {

    private final StudentService studentService;

    /**
     * Helper to get the authenticated user's ID from the security context (JWT principal).
     */
    private Long getCurrentUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    // --- Attendance Marking (Feature 3) ---

    @PostMapping("/checkin")
    public ResponseEntity<AttendanceDTO> studentCheckIn(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        AttendanceDTO record = studentService.checkIn(userId, LocalDateTime.now());
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }
    
    // --- Leave Management (Feature 5) ---

    @PostMapping("/leave/apply/class")
    public ResponseEntity<LeaveRequestDTO> applyForClassLeave(
            Authentication authentication,
            @Valid @RequestBody LeaveRequestDTO request) {
        
        Long userId = getCurrentUserId(authentication);
        LeaveRequestDTO newRequest = studentService.applyForClassLeave(userId, request);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
    }

    // --- Dashboard & Analytics (Feature 7) ---

    @GetMapping("/dashboard/attendance-percentage/{courseId}")
    public ResponseEntity<Double> getAttendancePercentage(
            Authentication authentication,
            @PathVariable Long courseId) {
        
        Long userId = getCurrentUserId(authentication);
        Double percentage = studentService.getAttendancePercentage(userId, courseId);
        return ResponseEntity.ok(percentage);
    }
    
    // --- Notifications (Feature 6) ---

    @GetMapping("/alerts/low-attendance")
    public ResponseEntity<List<String>> getLowAttendanceAlerts(Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        List<String> alerts = studentService.getLowAttendanceAlerts(userId);
        return ResponseEntity.ok(alerts);
    }
}

