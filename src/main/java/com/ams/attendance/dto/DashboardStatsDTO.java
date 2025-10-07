package com.ams.attendance.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardStatsDTO {

    // --- Core Personal/System Metrics ---
    
    private String userName;
    private String role;
    
    // Total Days/Records
    private int totalWorkingDays; 
    private int totalAttendanceRecords;
    
    // --- Attendance Summary ---
    
    // E.g., 92.5
    private Double attendancePercentage; 
    
    // Time metrics (in hours)
    private Double totalHoursPresent; 
    private Double averageCheckInTime; // Average check-in time (e.g., 9.20 for 9:20 AM)

    // Count of Present/Absent/Late
    private long presentCount;
    private long absentCount;
    private long lateCount; 
    
    // --- Leave Summary ---
    
    private long approvedLeaveCount;
    private long pendingLeaveCount;

    // --- Admin/Teacher Specific Metrics ---
    
    // E.g., Total active users in the system
    private long totalUsers;
    
    // E.g., Map for chart visualization (Key: Department Name, Value: Attendance %)
    private Map<String, Double> departmentAttendanceBreakdown; 
    
    // E.g., Top 5 users with lowest attendance
    private Map<String, Double> topDefaulters; 
}

