package com.ams.attendance.service;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.dto.DashboardStatsDTO;
import com.ams.attendance.entity.Attendance;
import com.ams.attendance.entity.User;
import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.enums.AttendanceStatus;
import com.ams.attendance.enums.LeaveStatus;
import com.ams.attendance.repository.AttendanceRepository;
import com.ams.attendance.repository.LeaveRepository;
import com.ams.attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository; // Needed for Leave application

    // Assuming a simplified convertToDto(LeaveRequest) exists in LeaveService or here. 
    // For now, we reuse the pattern from LeaveService
    // NOTE: In a real app, this should be an injected helper class.
    private LeaveRequestDTO convertToLeaveDto(LeaveRequest request) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(request.getId());
        dto.setApplicantId(request.getApplicant().getId());
        dto.setApplicantName(request.getApplicant().getName());
        dto.setStartDate(request.getStartDate());
        dto.setEndDate(request.getEndDate());
        dto.setReason(request.getReason());
        dto.setStatus(request.getStatus());
        dto.setRequestedOn(request.getRequestedOn());
        return dto;
    }

    /**
     * 1. Records a new self Check-in event for a student. (Feature 3)
     * This is simplified, assuming students track arrival time for classes/labs.
     */
    public AttendanceDTO checkIn(Long userId, LocalDateTime time) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Student user not found."));
        
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setCheckInTime(time);
        attendance.setStatus(AttendanceStatus.PRESENT);
        
        Attendance savedAttendance = attendanceRepository.save(attendance);
        // NOTE: A proper conversion to AttendanceDTO requires more logic, 
        // but for now, we return a new DTO instance.
        AttendanceDTO dto = new AttendanceDTO();
        dto.setUserId(userId);
        dto.setCheckInTime(savedAttendance.getCheckInTime());
        dto.setStatus(AttendanceStatus.PRESENT);
        return dto;
    }

    /**
     * 2. Allows a student to apply for class-specific leave. (Feature 5)
     * This delegates logic to the LeaveRepository but sets a student-specific context.
     */
    public LeaveRequestDTO applyForClassLeave(Long userId, LeaveRequestDTO requestDto) {
        User applicant = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Applicant user not found."));

        LeaveRequest request = new LeaveRequest();
        request.setApplicant(applicant);
        request.setStartDate(requestDto.getStartDate());
        request.setEndDate(requestDto.getEndDate());
        request.setReason(requestDto.getReason());
        request.setStatus(LeaveStatus.PENDING);
        request.setRequestedOn(LocalDateTime.now());

        LeaveRequest savedRequest = leaveRepository.save(request);
        return convertToLeaveDto(savedRequest);
    }

    /**
     * 3. Calculates the current attendance percentage for a specific course. (Feature 7)
     * This is the personal metric for the student dashboard.
     */
    public Double getAttendancePercentage(Long userId, Long courseId) {
        // Find records for the student and course that are not 'ABSENT' (i.e., PRESENT, LATE, or ON_LEAVE)
        List<Attendance> allRecords = attendanceRepository
            .findByUser_IdAndCourse_Id(userId, courseId);
        
        long totalClasses = allRecords.size();
        if (totalClasses == 0) return 100.0; // Assume 100% if no data yet

        long presentCount = allRecords.stream()
            .filter(a -> a.getStatus() == AttendanceStatus.PRESENT || a.getStatus() == AttendanceStatus.LATE)
            .count();
        
        // This is a simplified calculation. Real logic needs to account for scheduled vs actual classes.
        return (double) Math.round((presentCount * 100.0 / totalClasses) * 100) / 100;
    }
    
    /**
     * 4. Views low attendance alerts. (Feature 6)
     * This utilizes the attendance percentage logic to generate an alert list.
     */
    public List<String> getLowAttendanceAlerts(Long userId) {
        // NOTE: In a real system, we'd fetch the student's enrolled courses here.
        // For demonstration, we simulate checking courses 1, 2, and 3.
        List<Long> enrolledCourseIds = List.of(1L, 2L, 3L); 
        final double THRESHOLD = 75.0;
        
        return enrolledCourseIds.stream()
            .map(courseId -> {
                Double percentage = getAttendancePercentage(userId, courseId);
                if (percentage < THRESHOLD) {
                    return "Alert: Attendance for Course " + courseId + " is " + percentage + "%, which is below the required 75%.";
                }
                return null;
            })
            .filter(alert -> alert != null)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves aggregated student dashboard statistics. (Feature 7)
     * This uses the core metrics defined in the DashboardStatsDTO.
     */
    public DashboardStatsDTO getDashboardStats(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Student user not found."));
        
        // --- Placeholder Data Aggregation ---
        List<Attendance> allStudentAttendance = attendanceRepository.findByUser_Id(userId);
        
        long present = allStudentAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = allStudentAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long totalDays = allStudentAttendance.size();

        // Calculate a simple percentage for the dashboard
        Double attendancePct = (totalDays > 0) ? (double) Math.round((present * 100.0 / totalDays) * 100) / 100 : 100.0;
        
        // --- Build DTO ---
        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setUserName(user.getName());
        dto.setRole(user.getRole().name());
        
        dto.setTotalAttendanceRecords((int) totalDays);
        dto.setPresentCount(present);
        dto.setAbsentCount(absent);
        dto.setAttendancePercentage(attendancePct);
        
        // Leave counts (simplified)
        dto.setApprovedLeaveCount(leaveRepository.countByApplicant_IdAndStatus(userId, LeaveStatus.APPROVED));
        dto.setPendingLeaveCount(leaveRepository.countByApplicant_IdAndStatus(userId, LeaveStatus.PENDING));

        // Other fields like totalHoursPresent remain 0.0 without complex calculations.
        
        return dto;
    }
}
