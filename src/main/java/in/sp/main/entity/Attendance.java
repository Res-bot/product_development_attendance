package in.sp.main.entity;

import java.time.LocalDateTime; // <-- CORRECTED IMPORT

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Attendance {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId; 
	    
	    // The time the employee checked in
	    private LocalDateTime checkInTime; // <-- CORRECTED TYPE

	    // The time the employee checked out (nullable)
	    private LocalDateTime checkOutTime; // <-- CORRECTED TYPE

	    private String checkInNotes;

		// --- Getters and Setters (Updated for LocalDateTime) ---

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

		public LocalDateTime getCheckInTime() { // <-- CORRECTED TYPE
			return checkInTime;
		}

		public void setCheckInTime(LocalDateTime checkInTime) { // <-- CORRECTED TYPE
			this.checkInTime = checkInTime;
		}

		public LocalDateTime getCheckOutTime() { // <-- CORRECTED TYPE
			return checkOutTime;
		}

		public void setCheckOutTime(LocalDateTime checkOutTime) { // <-- CORRECTED TYPE
			this.checkOutTime = checkOutTime;
		}

		public String getCheckInNotes() {
			return checkInNotes;
		}

		public void setCheckInNotes(String checkInNotes) {
			this.checkInNotes = checkInNotes;
		}
        
        // --- Constructors and toString (Updated for LocalDateTime) ---

		public Attendance(Long id, Long userId, LocalDateTime checkInTime, LocalDateTime checkOutTime, String checkInNotes) {
			super();
			this.id = id;
			this.userId = userId;
			this.checkInTime = checkInTime;
			this.checkOutTime = checkOutTime;
			this.checkInNotes = checkInNotes;
		}

		public Attendance() {
			super();
		}

		@Override
		public String toString() {
			return "Attendance [id=" + id + ", userId=" + userId + ", checkInTime=" + checkInTime + ", checkOutTime="
					+ checkOutTime + ", checkInNotes=" + checkInNotes + "]";
		}
}