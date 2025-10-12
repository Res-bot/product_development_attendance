package in.sp.main.dto;

import java.time.LocalDate;

import in.sp.main.enums.Leavestat;

public class Leavereqdto {
	 private Long id;
	    private Long userId;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String reason;
	    private Leavestat status; 
	    
	    
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    public Long getUserId() { return userId; }
	    public void setUserId(Long userId) { this.userId = userId; }
	    public LocalDate getStartDate() { return startDate; }
	    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
	    public LocalDate getEndDate() { return endDate; }
	    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
	    public String getReason() { return reason; }
	    public void setReason(String reason) { this.reason = reason; }
	    public Leavestat getStatus() { return status; }
	    public void setStatus(Leavestat status) { this.status = status; }

}
