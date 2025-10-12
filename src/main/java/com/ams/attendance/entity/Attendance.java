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
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Attendance(Long id, User user, Course course, LocalDateTime checkInTime, LocalDateTime checkOutTime,
			AttendanceStatus status, String notes) {
		super();
		this.id = id;
		this.user = user;
		this.course = course;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.status = status;
		this.notes = notes;
	}

	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", user=" + user + ", course=" + course + ", checkInTime=" + checkInTime
				+ ", checkOutTime=" + checkOutTime + ", status=" + status + ", notes=" + notes + "]";
	}
    
    
}
