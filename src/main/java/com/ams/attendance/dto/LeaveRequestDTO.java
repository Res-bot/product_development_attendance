package com.ams.attendance.dto;

import com.ams.attendance.enums.LeaveStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveRequestDTO {

    private Long id;

    // Fields required for submitting a request
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "Reason for leave is required")
    private String reason;
    
    // --- Related User Information (for display/submission context) ---
    
    // The ID of the user applying for leave (sent by frontend or derived in service from JWT)
    private Long applicantId; 
    
    // Name of the applicant (read-only for display)
    private String applicantName; 

    // --- Status and Approval Information ---
    
    private LeaveStatus status; // PENDING, APPROVED, REJECTED (from enums package)

    private LocalDateTime requestedOn;
    
    // The ID and Name of the approver (read-only for display)
    private Long approverId;
    private String approverName;
    
    private String rejectionReason;
}

