package com.ams.attendance.service;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.enums.LeaveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    // private final AttendanceRepository attendanceRepository;
    // private final LeaveRepository leaveRepository;

    private final LeaveService leaveService;

    // 1. Manual Attendance Marking (Feature 3)
    public void markAttendanceByClass(Long courseId, List<AttendanceDTO> attendanceList) {
        // Logic to manually mark attendance for all students in a course/class
    }

    // 2. Leave Request Approval/Rejection (Feature 5)
    public LeaveRequestDTO reviewLeaveRequest(Long requestId,Long approverId, LeaveStatus status, String notes) {
        // Logic to fetch leave request and set status to APPROVED or REJECTED
        return new LeaveRequestDTO(); 
    }

    // 3. Get Course/Class Attendance Report (Feature 4)
    public List<AttendanceDTO> getCourseAttendanceReport(Long courseId, LocalDate from, LocalDate to) {
        // Logic to fetch aggregated attendance for a specific course
        return List.of();
    }
    
    // 4. Get List of Students for a Course
    public List<Long> getStudentsByCourse(Long courseId) {
        // Logic to fetch all user IDs (students) enrolled in a specific course/batch
        return List.of();
    }

    /**
     * Retrieves all PENDING leave requests for Teacher review. (Feature 5)
     * Delegates the logic to LeaveService.
     */
    public List<LeaveRequestDTO> getPendingLeaveRequests() {
        return leaveService.getAllPendingRequests();
    }
}
