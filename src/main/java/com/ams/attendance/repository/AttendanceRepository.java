package com.ams.attendance.repository;

import com.ams.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Finds the latest Attendance record for a user that does not have a check-out time.
     * Used by the checkOut method in AttendanceService.
     * * findTop...By...OrderBy...Desc is a derived query signature.
     */
    Optional<Attendance> findTopByUser_IdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(Long userId);

    /**
     * Retrieves all attendance records for a specific user within a date/time range.
     * Used by getAttendanceReportByUser in AttendanceService.
     */
    List<Attendance> findByUser_IdAndCheckInTimeBetweenOrderByCheckInTimeAsc(
        Long userId, 
        LocalDateTime startDateTime, 
        LocalDateTime endDateTime
    );

    // --- NEW METHOD for StudentService (Feature 7) ---
    /**
     * Finds all attendance records for a specific user and course.
     * Used by getAttendancePercentage in StudentService.
     */
    List<Attendance> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    // --- NEW METHOD for StudentService Dashboard (Feature 7) ---
    /**
     * Finds all attendance records for a specific user.
     * Used by getDashboardStats in StudentService.
     */
    List<Attendance> findByUser_Id(Long userId);
   
    public findByUserIdAndAttendanceDateBetween(userId, startDate, endDate) {
    	
    }
}  

