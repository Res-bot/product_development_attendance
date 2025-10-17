package com.example.attendmanage.dto;

import com.example.attendmanage.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AttendanceDTO {

    private Long id;
    
    
    @NotNull(message = "User ID is required for attendance tracking")
    private Long userId; 
    
    private String userName; 
    
    private Long courseId; 
    

    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime; 

    private LocalDateTime checkOutTime; 

    private Double totalHours; 
    
    
    private AttendanceStatus status; 

    private String notes; 

    private LocalDate attendanceDate;
}

