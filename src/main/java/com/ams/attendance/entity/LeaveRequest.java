package com.ams.attendance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ams.attendance.enums.LeaveStatus;

@Entity
@Table(name = "leave_requests")
@Data
@NoArgsConstructor
public class LeaveRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship: Many Leave Requests belong to One User (the applicant)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;
    
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;
    
    @Column(nullable = false)
    private LocalDateTime requestedOn = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status; // PENDING, APPROVED, REJECTED (You'll need to create LeaveStatus enum)

    // Relationship: The user who approved/rejected the request (e.g., Admin/Teacher/Manager)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User approver;

    private String rejectionReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDateTime getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(LocalDateTime requestedOn) {
		this.requestedOn = requestedOn;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public User getApprover() {
		return approver;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public LeaveRequest(Long id, User applicant, LocalDate startDate, LocalDate endDate, String reason,
			LocalDateTime requestedOn, LeaveStatus status, User approver, String rejectionReason) {
		super();
		this.id = id;
		this.applicant = applicant;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.requestedOn = requestedOn;
		this.status = status;
		this.approver = approver;
		this.rejectionReason = rejectionReason;
	}

	public LeaveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LeaveRequest [id=" + id + ", applicant=" + applicant + ", startDate=" + startDate + ", endDate="
				+ endDate + ", reason=" + reason + ", requestedOn=" + requestedOn + ", status=" + status + ", approver="
				+ approver + ", rejectionReason=" + rejectionReason + "]";
	}
    
    
}
