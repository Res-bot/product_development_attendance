package com.example.attendmanage.service;

import com.example.attendmanage.dto.AttendanceDTO;
import com.example.attendmanage.dto.LeaveRequestDTO;
import com.example.attendmanage.dto.DashboardStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    public AttendanceDTO checkIn(Long userId, LocalDateTime time, String notes) {
        return new AttendanceDTO();
    }

    public AttendanceDTO checkOut(Long userId, LocalDateTime time) {
        return new AttendanceDTO();
    }

    public LeaveRequestDTO applyForLeave(Long userId, LeaveRequestDTO request) {
        return new LeaveRequestDTO();
    }

    public DashboardStatsDTO getPersonalStats(Long userId) {
        return new DashboardStatsDTO();
    }
}