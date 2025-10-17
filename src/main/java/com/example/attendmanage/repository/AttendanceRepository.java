package com.example.attendmanage.repository;

import com.example.attendmanage.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 
import org.springframework.stereotype.Repository; 

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository 
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findTopByUser_IdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(Long userId);

    List<Attendance> findByUser_IdAndCheckInTimeBetweenOrderByCheckInTimeAsc(
        Long userId, 
        LocalDateTime startDateTime, 
        LocalDateTime endDateTime
    );


    List<Attendance> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    List<Attendance> findByUser_Id(Long userId);

    List<Attendance> findByCourse_IdAndCheckInTimeBetween(Long courseId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT a.user.id FROM Attendance a WHERE a.course.id = :courseId")
    List<Long> findDistinctUserIdsByCourseId(@Param("courseId") Long courseId);
}
