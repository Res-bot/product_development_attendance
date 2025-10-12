package com.ams.attendance.dto;

import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.enums.LeaveStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveRequestDTO {

    private Long id;

    // Fields required for submitting a request
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotEmpty(message = "Reason for leave is required")
    private String reason;
    
    // --- Related User Information (for display/submission context) ---
    
    // The ID of the user applying for leave (sent by frontend or derived in service from JWT)
    private Long applicantId; 
    
    // Name of the applicant (read-only for display)
    private String applicantName; 

    // --- Status and Approval Information ---
    
    private LeaveStatus status; // PENDING, APPROVED, REJECTED (from enums package)

    private LocalDateTime requestedOn;
    
    // The ID and Name of the approver (read-only for display)
    private Long approverId;
    private String approverName;
    
    private String rejectionReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public LocalDateTime getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(LocalDateTime requestedOn) {
		this.requestedOn = requestedOn;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public LeaveRequestDTO(Long id,
			@NotNull(message = "Start date is required") @FutureOrPresent(message = "Start date must be today or in the future") LocalDate startDate,
			@NotNull(message = "End date is required") LocalDate endDate,
			@NotEmpty(message = "Reason for leave is required") String reason, Long applicantId, String applicantName,
			LeaveStatus status, LocalDateTime requestedOn, Long approverId, String approverName,
			String rejectionReason) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.applicantId = applicantId;
		this.applicantName = applicantName;
		this.status = status;
		this.requestedOn = requestedOn;
		this.approverId = approverId;
		this.approverName = approverName;
		this.rejectionReason = rejectionReason;
	}

	public LeaveRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LeaveRequestDTO [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", reason=" + reason
				+ ", applicantId=" + applicantId + ", applicantName=" + applicantName + ", status=" + status
				+ ", requestedOn=" + requestedOn + ", approverId=" + approverId + ", approverName=" + approverName
				+ ", rejectionReason=" + rejectionReason + "]";
	}
	
	public static LeaveRequestDTO fromEntity(LeaveRequest entity) {
	    LeaveRequestDTO dto = new LeaveRequestDTO();
	    dto.setId(entity.getId());
	    dto.setStartDate(entity.getStartDate());
	    dto.setEndDate(entity.getEndDate());
	    dto.setReason(entity.getReason());
	    dto.setStatus(entity.getStatus());
	    dto.setRequestedOn(entity.getRequestedOn());
	    dto.setApplicantId(entity.getApplicant().getId());
	    dto.setApplicantName(entity.getApplicant().getName());
	    if (entity.getApprover() != null) {
	        dto.setApproverId(entity.getApprover().getId());
	        dto.setApproverName(entity.getApprover().getName());
	    }
	    dto.setRejectionReason(entity.getRejectionReason());
	    return dto;
	}

    
    
}

