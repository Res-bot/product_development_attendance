package com.ams.attendance.dto;

import com.ams.attendance.enums.AttendanceStatus;
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
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Double getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

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

