package com.ams.attendance.controller;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.enums.LeaveStatus;
import com.ams.attendance.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')") // Base authorization
public class TeacherController {

    private TeacherService teacherService;

    // --- Attendance Marking (Feature 3) ---

    @PostMapping("/attendance/mark/{courseId}")
    public ResponseEntity<Void> markAttendanceByClass(
            @PathVariable Long courseId,
            @Valid @RequestBody List<AttendanceDTO> attendanceList) {
        
        teacherService.markAttendanceByClass(courseId, attendanceList);
        return ResponseEntity.ok().build();
    }
    
    // --- Leave Management (Feature 5) ---

    @GetMapping("/leave/pending")
    public ResponseEntity<List<LeaveRequestDTO>> getPendingLeaveRequests() {
        // This call is now correctly directed to the method defined in TeacherService
        return ResponseEntity.ok(teacherService.getPendingLeaveRequests()); 
    }

    @PutMapping("/leave/review/{requestId}")
    public ResponseEntity<LeaveRequestDTO> reviewLeaveRequest(
            @PathVariable Long requestId,
            @RequestParam LeaveStatus status,
            @RequestParam(required = false) String notes) {
        
        // In a real app, retrieve approverId from security context (JWT)
        // NOTE: This placeholder ID should be replaced with logic to extract the ID from the Authentication principal.
        Long approverId = 1L; 

        LeaveRequestDTO updatedRequest = teacherService.reviewLeaveRequest(requestId, approverId, status, notes);
        return ResponseEntity.ok(updatedRequest);
    }
    
    // --- Reporting (Feature 4) ---

    @GetMapping("/reports/course/{courseId}")
    public ResponseEntity<List<AttendanceDTO>> getCourseReport(
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        
        List<AttendanceDTO> report = teacherService.getCourseAttendanceReport(courseId, from, to);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<List<Long>> getStudentsInCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(teacherService.getStudentsByCourse(courseId));
    }
}