package com.ams.attendance.service;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.dto.DashboardStatsDTO;
import com.ams.attendance.entity.Attendance;
import com.ams.attendance.entity.Course;
import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.entity.User;
import com.ams.attendance.enums.AttendanceStatus;
import com.ams.attendance.enums.LeaveStatus;
import com.ams.attendance.repository.AttendanceRepository;
import com.ams.attendance.repository.CourseRepository;
import com.ams.attendance.repository.LeaveRepository;
import com.ams.attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
	
    @Autowired
    private  CourseRepository courseRepository;
    private  AttendanceRepository attendanceRepository;
    private  LeaveRepository leaveRepository;
    private  UserRepository userRepository;


    // --- Helper to convert LeaveRequest to DTO ---
    private LeaveRequestDTO convertToLeaveDto(LeaveRequest request) {
        return LeaveRequestDTO.fromEntity(request);
    }

    // -------------------------
    // 1. Student Check-in
    // -------------------------
    public AttendanceDTO checkIn(Long userId, Long courseId, LocalDateTime time) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student user not found."));

        Attendance attendance = new Attendance();
        attendance.setUser(user);

        if (courseId != null) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            attendance.setCourse(course);
        }

        attendance.setCheckInTime(time);
        attendance.setStatus(AttendanceStatus.PRESENT);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        AttendanceDTO dto = new AttendanceDTO();
        dto.setUserId(userId);
        dto.setCheckInTime(savedAttendance.getCheckInTime());
        dto.setStatus(AttendanceStatus.PRESENT);
        dto.setCourseId(courseId);
        return dto;
    }

    public AttendanceDTO checkIn(Long userId, LocalDateTime time) {
        return checkIn(userId, null, time);
    }

    // -------------------------
    // 2. Apply for Class Leave
    // -------------------------
    public LeaveRequestDTO applyForClassLeave(Long userId, LeaveRequestDTO requestDto) {
        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Applicant not found"));

        LeaveRequest request = new LeaveRequest();
        request.setApplicant(applicant);
        request.setStartDate(requestDto.getStartDate());
        request.setEndDate(requestDto.getEndDate());
        request.setReason(requestDto.getReason());
        request.setStatus(LeaveStatus.PENDING);
        request.setRequestedOn(LocalDateTime.now());

        LeaveRequest saved = leaveRepository.save(request);

        return convertToLeaveDto(saved);
    }

    // -------------------------
    // 3. Calculate Attendance %
    // -------------------------
    public Double getAttendancePercentage(Long userId, Long courseId) {
        List<Attendance> allRecords = attendanceRepository.findByUser_IdAndCourse_Id(userId, courseId);
        if (allRecords.isEmpty()) return 100.0;

        long presentCount = allRecords.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT || a.getStatus() == AttendanceStatus.LATE)
                .count();

        double percentage = presentCount * 100.0 / allRecords.size();
        return Math.round(percentage * 100) / 100.0;
    }

    // -------------------------
    // 4. Low Attendance Alerts
    // -------------------------
    public List<String> getLowAttendanceAlerts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Set<Long> enrolledCourseIds = user.getCourses().stream()
                .map(c -> c.getId())
                .collect(Collectors.toSet());

        final double THRESHOLD = 75.0;

        return enrolledCourseIds.stream()
                .map(courseId -> {
                    Double percentage = getAttendancePercentage(userId, courseId);
                    if (percentage < THRESHOLD) {
                        return "Alert: Attendance for Course " + courseId + " is " + percentage +
                                "%, below required 75%";
                    }
                    return null;
                })
                .filter(alert -> alert != null)
                .collect(Collectors.toList());
    }

    // -------------------------
    // 5. Student Dashboard Stats
    // -------------------------
    public DashboardStatsDTO getDashboardStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Attendance> allAttendance = attendanceRepository.findByUser_Id(userId);

        long present = allAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
        long absent = allAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long late = allAttendance.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
        long totalDays = allAttendance.size();

        Double attendancePct = (totalDays > 0) ? Math.round(present * 100.0 / totalDays * 100) / 100.0 : 100.0;

        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setUserName(user.getName());
        dto.setRole(user.getRole().name());
        dto.setTotalAttendanceRecords((int) totalDays);
        dto.setPresentCount(present);
        dto.setAbsentCount(absent);
        dto.setLateCount(late);
        dto.setAttendancePercentage(attendancePct);

        dto.setApprovedLeaveCount(leaveRepository.countByApplicant_IdAndStatus(userId, LeaveStatus.APPROVED));
        dto.setPendingLeaveCount(leaveRepository.countByApplicant_IdAndStatus(userId, LeaveStatus.PENDING));

        dto.setTotalHoursPresent(allAttendance.stream()
                .filter(a -> a.getCheckInTime() != null && a.getCheckOutTime() != null)
                .mapToDouble(a -> (a.getCheckOutTime().getHour() + a.getCheckOutTime().getMinute() / 60.0)
                        - (a.getCheckInTime().getHour() + a.getCheckInTime().getMinute() / 60.0))
                .sum());

        return dto;
    }

}
