package com.example.attendmanage.service;

import com.example.attendmanage.dto.AttendanceDTO;
import com.example.attendmanage.dto.LeaveRequestDTO;
import com.example.attendmanage.entity.Attendance;
import com.example.attendmanage.entity.Course;
import com.example.attendmanage.entity.LeaveRequest;
import com.example.attendmanage.entity.User;
import com.example.attendmanage.enums.AttendanceStatus;
import com.example.attendmanage.enums.LeaveStatus;
import com.example.attendmanage.repository.AttendanceRepository;
import com.example.attendmanage.repository.CourseRepository;
import com.example.attendmanage.repository.LeaveRepository;
import com.example.attendmanage.repository.UserRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final AttendanceRepository attendanceRepository;
    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LeaveService leaveService;
    private final EmailService emailService; 

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

    public List<Long> getStudentsByCourse(Long courseId) {
        return attendanceRepository.findDistinctUserIdsByCourseId(courseId);
    }

    public List<LeaveRequestDTO> getPendingLeaveRequests() {
        return leaveService.getAllPendingRequests();
    }

    @Getter
    public static class StudentEmailInfo { 
        private final String email;
        private final String subject;
        private final String body;

        public StudentEmailInfo(String email, String subject, String body) {
            this.email = email;
            this.subject = subject;
            this.body = body;
        }
    }

    
    public List<StudentEmailInfo> getAbsentStudentEmailInfo(Long courseId, LocalDate attendanceDate) {
        List<Long> allStudentIds = getStudentsByCourse(courseId);
        
        String courseName = courseRepository.findById(courseId)
            .map(Course::getName) 
            .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        LocalDateTime start = attendanceDate.atStartOfDay();
        LocalDateTime end = attendanceDate.plusDays(1).atStartOfDay();

        Set<Long> presentStudentIds = attendanceRepository
                .findByCourse_IdAndCheckInTimeBetween(courseId, start, end)
                .stream()
                .filter(att -> att.getStatus() != AttendanceStatus.ABSENT)
                .map(att -> att.getUser().getId())
                .collect(Collectors.toSet());

        List<Long> absentStudentIds = allStudentIds.stream()
                .filter(id -> !presentStudentIds.contains(id))
                .collect(Collectors.toList());

        List<User> absentUsers = userRepository.findAllById(absentStudentIds); 

        String subject = "Absence Notification for " + courseName + " on " + attendanceDate.toString();

        return absentUsers.stream()
            .map(user -> {
                String body = String.format(
                    "Dear %s,\n\nOur records show that you were marked absent for the class (%s) on %s. "
                    + "Please follow up with your teacher if you believe this is an error.\n\n"
                    + "Regards,\nTeacher Service",
                    user.getName(), 
                    courseName,
                    attendanceDate.toString()
                );
                return new StudentEmailInfo(user.getEmail(), subject, body);
            })
            .collect(Collectors.toList());
    }
}


