package com.ams.attendance.service;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.entity.Attendance;
import com.ams.attendance.entity.Course;
import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.entity.User;
import com.ams.attendance.enums.LeaveStatus;
import com.ams.attendance.repository.AttendanceRepository;
import com.ams.attendance.repository.CourseRepository;
import com.ams.attendance.repository.LeaveRepository;
import com.ams.attendance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private  AttendanceRepository attendanceRepository;
    private  LeaveRepository leaveRepository;
    private  UserRepository userRepository;
    private  CourseRepository courseRepository;
    private  LeaveService leaveService;

    // 1️⃣ Manual Attendance Marking
    public void markAttendanceByClass(Long courseId, List<AttendanceDTO> attendanceList) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        List<Attendance> records = attendanceList.stream().map(dto -> {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + dto.getUserId()));

            Attendance att = new Attendance();
            att.setUser(user);
            att.setCourse(course);
            att.setCheckInTime(dto.getCheckInTime());
            att.setCheckOutTime(dto.getCheckOutTime());
            att.setStatus(dto.getStatus());
            att.setNotes(dto.getNotes());
            return att;
        }).collect(Collectors.toList());

        attendanceRepository.saveAll(records);
    }

    // 2️⃣ Leave Request Approval / Rejection
    public LeaveRequestDTO reviewLeaveRequest(Long requestId, Long approverId, LeaveStatus status, String notes) {
        LeaveRequest request = leaveRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found with ID: " + requestId));

        User approver = userRepository.findById(approverId)
                .orElseThrow(() -> new IllegalArgumentException("Approver not found with ID: " + approverId));

        request.setStatus(status);
        request.setApprover(approver);
        if (status == LeaveStatus.REJECTED) {
            request.setRejectionReason(notes);
        }

        LeaveRequest saved = leaveRepository.save(request);
        return LeaveRequestDTO.fromEntity(saved);
    }

    // 3️⃣ Get Course/Class Attendance Report
    public List<AttendanceDTO> getCourseAttendanceReport(Long courseId, LocalDate from, LocalDate to) {
        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.plusDays(1).atStartOfDay(); // include entire end day

        List<Attendance> records = attendanceRepository.findByCourse_IdAndCheckInTimeBetween(courseId, start, end);

        return records.stream().map(att -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(att.getId());
            dto.setUserId(att.getUser().getId());
            dto.setUserName(att.getUser().getName());
            dto.setCourseId(att.getCourse().getId());
            dto.setCheckInTime(att.getCheckInTime());
            dto.setCheckOutTime(att.getCheckOutTime());
            dto.setStatus(att.getStatus());
            dto.setNotes(att.getNotes());
            return dto;
        }).collect(Collectors.toList());
    }

    // 4️⃣ Get List of Students for a Course
    public List<Long> getStudentsByCourse(Long courseId) {
        return attendanceRepository.findDistinctUserIdsByCourseId(courseId);
    }

    // 5️⃣ Get all pending leave requests (delegated to LeaveService)
    public List<LeaveRequestDTO> getPendingLeaveRequests() {
        return leaveService.getAllPendingRequests();
    }
}
