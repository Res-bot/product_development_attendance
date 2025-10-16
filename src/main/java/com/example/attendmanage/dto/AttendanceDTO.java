package com.example.attendmanage.dto;

import com.example.attendmanage.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceDTO {

    private Long id;
    
    // --- User/Course Context ---
    
    // ID of the user whose attendance is being recorded (required for all actions)
    @NotNull(message = "User ID is required for attendance tracking")
    private Long userId; 
    
    // Read-only user name for reporting/display
    private String userName; 
    
    // Optional: Course ID if tracking class-specific attendance
    private Long courseId; 
    
    // --- Timestamps ---

    // Time when the user checked in (required for check-in)
    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime; 

    // Time when the user checked out (null for active session)
    private LocalDateTime checkOutTime; 

    // Calculated fields
    private Double totalHours; 
    
    // --- Status & Notes ---
    
    // Status (PRESENT, ABSENT, LATE, etc.)
    private AttendanceStatus status; 

    private String notes; // e.g., location/reason

    // Read-only date for filtering
    private LocalDate attendanceDate;
}

