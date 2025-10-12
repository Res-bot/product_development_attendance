package com.ams.attendance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.ams.attendance.enums.AttendanceStatus; 

@Entity
@Table(name = "attendance_records")
@Data
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship: Many Attendance records belong to One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional: If attendance is tracked per course/subject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime; // Nullable for active session

    // To handle manual marking (Present/Absent/Late) or check-in status (In/Out)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status; // You'll need to create AttendanceStatus enum

    private String notes; // e.g., location/reason for manual attendance

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getCheckOutTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getCheckInTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNotes() {
		// TODO Auto-generated method stub
		return null;
	}
}
