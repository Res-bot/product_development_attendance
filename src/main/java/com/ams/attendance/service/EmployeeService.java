package com.ams.attendance.service;

import com.ams.attendance.dto.AttendanceDTO;
import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.entity.Attendance;
import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.repository.LeaveRepository;
import com.ams.attendance.dto.DashboardStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    // private final AttendanceRepository attendanceRepository;
    // private final LeaveRepository leaveRepository;

    private Object attendanceRepository;


	// 1. Self Check-in (Feature 3)
    public AttendanceDTO checkIn(Long userId, LocalDateTime time, String notes) {
        // Logic to create a new Attendance record with check-in time
        return new AttendanceDTO();
    }

    // 2. Self Check-out (Feature 3)
    public AttendanceDTO checkOut(Long userId, LocalDateTime time) {
        // Logic to find the latest open Attendance record and update check-out time
        return new AttendanceDTO();
    }

    // 3. Apply for Leave (Feature 5)
    public LeaveRequestDTO applyForLeave(Long userId, LeaveRequestDTO request) {
        // Logic to create a new LeaveRequest entity with status PENDING
        return new LeaveRequestDTO();
    }

    // 4. View Leave History
    public List<LeaveRequestDTO> viewLeaveHistory(Long userId) {
        // Step 1: Fetch all leave request entities from the database for the given user ID.
        List<LeaveRequest> leaveHistoryEntities = LeaveRepository.findByUserId(userId);

        // Step 2: Convert the list of entity objects to a list of DTOs.
        return leaveHistoryEntities.stream()
                .map(this::convertToLeaveRequestDTO)
                .collect(Collectors.toList());
    }
    
   
    private LeaveRequestDTO convertToLeaveRequestDTO(LeaveRequest entity) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        // Map any other fields you have
        return dto;
    }
    
//    // 4. Get Personal Dashboard Stats (Feature 7)
//    public DashboardStatsDTO getPersonalStats(Long userId) {
//        // Logic to calculate total present/absent days and working hours for the employee
//        return new DashboardStatsDTO();
//    }
    
    
    public List<AttendanceDTO> getPersonalAttendanceReport(Long userId, LocalDate startDate, LocalDate endDate) {
        // Step 1: Fetch attendance records from the database for the user within the date range.
        // Note: You'll need a method like 'findByUserIdAndAttendanceDateBetween' in your AttendanceRepository.
        List<Attendance> attendanceRecords = attendanceRepository.findByUserIdAndAttendanceDateBetween(userId, startDate, endDate);

        // Step 2: Convert the list of Attendance entities to a list of AttendanceDTOs.
        return attendanceRecords.stream()
                .map(this::convertToAttendanceDTO)
                .collect(Collectors.toList());
    }


    // 4. Get Personal Dashboard Stats (Feature 7)
    public DashboardStatsDTO getPersonalStats(Long userId) {
        // Logic to calculate total present/absent days and working hours for the employee
        return new DashboardStatsDTO();
    }
    
    private AttendanceDTO convertToAttendanceDTO(Attendance entity) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setCheckInTime(entity.getCheckInTime());
        dto.setCheckOutTime(entity.getCheckOutTime());
        dto.setNotes(entity.getNotes());
        // Map any other relevant fields
        return dto;
    }

}