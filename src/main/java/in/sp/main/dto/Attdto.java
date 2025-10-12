package in.sp.main.dto;

import java.time.LocalDateTime;

public class Attdto {
	private Long id;
    private Long userId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String checkInNotes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getCheckInNotes() {
		return checkInNotes;
	}
	public void setCheckInNotes(String checkInNotes) {
		this.checkInNotes = checkInNotes;
	}
	public Attdto(Long id, Long userId, LocalDateTime checkInTime, LocalDateTime checkOutTime, String checkInNotes) {
		super();
		this.id = id;
		this.userId = userId;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.checkInNotes = checkInNotes;
	}
	public Attdto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Attdto [id=" + id + ", userId=" + userId + ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + ", checkInNotes=" + checkInNotes + "]";
	}
    

}
