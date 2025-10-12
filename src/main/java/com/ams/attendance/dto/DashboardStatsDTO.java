package com.ams.attendance.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardStatsDTO {

    // --- Core Personal/System Metrics ---
    
    private String userName;
    private String role;
    
    // Total Days/Records
    private int totalWorkingDays; 
    private int totalAttendanceRecords;
    
    // --- Attendance Summary ---
    
    // E.g., 92.5
    private Double attendancePercentage; 
    
    // Time metrics (in hours)
    private Double totalHoursPresent; 
    private Double averageCheckInTime; // Average check-in time (e.g., 9.20 for 9:20 AM)

    // Count of Present/Absent/Late
    private long presentCount;
    private long absentCount;
    private long lateCount; 
    
    // --- Leave Summary ---
    
    private long approvedLeaveCount;
    private long pendingLeaveCount;

    // --- Admin/Teacher Specific Metrics ---
    
    // E.g., Total active users in the system
    private long totalUsers;
    
    // E.g., Map for chart visualization (Key: Department Name, Value: Attendance %)
    private Map<String, Double> departmentAttendanceBreakdown; 
    
    // E.g., Top 5 users with lowest attendance
    private Map<String, Double> topDefaulters;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(int totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public int getTotalAttendanceRecords() {
		return totalAttendanceRecords;
	}

	public void setTotalAttendanceRecords(int totalAttendanceRecords) {
		this.totalAttendanceRecords = totalAttendanceRecords;
	}

	public Double getAttendancePercentage() {
		return attendancePercentage;
	}

	public void setAttendancePercentage(Double attendancePercentage) {
		this.attendancePercentage = attendancePercentage;
	}

	public Double getTotalHoursPresent() {
		return totalHoursPresent;
	}

	public void setTotalHoursPresent(Double totalHoursPresent) {
		this.totalHoursPresent = totalHoursPresent;
	}

	public Double getAverageCheckInTime() {
		return averageCheckInTime;
	}

	public void setAverageCheckInTime(Double averageCheckInTime) {
		this.averageCheckInTime = averageCheckInTime;
	}

	public long getPresentCount() {
		return presentCount;
	}

	public void setPresentCount(long presentCount) {
		this.presentCount = presentCount;
	}

	public long getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(long absentCount) {
		this.absentCount = absentCount;
	}

	public long getLateCount() {
		return lateCount;
	}

	public void setLateCount(long lateCount) {
		this.lateCount = lateCount;
	}

	public long getApprovedLeaveCount() {
		return approvedLeaveCount;
	}

	public void setApprovedLeaveCount(long approvedLeaveCount) {
		this.approvedLeaveCount = approvedLeaveCount;
	}

	public long getPendingLeaveCount() {
		return pendingLeaveCount;
	}

	public void setPendingLeaveCount(long pendingLeaveCount) {
		this.pendingLeaveCount = pendingLeaveCount;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public Map<String, Double> getDepartmentAttendanceBreakdown() {
		return departmentAttendanceBreakdown;
	}

	public void setDepartmentAttendanceBreakdown(Map<String, Double> departmentAttendanceBreakdown) {
		this.departmentAttendanceBreakdown = departmentAttendanceBreakdown;
	}

	public Map<String, Double> getTopDefaulters() {
		return topDefaulters;
	}

	public void setTopDefaulters(Map<String, Double> topDefaulters) {
		this.topDefaulters = topDefaulters;
	}

	public DashboardStatsDTO(String userName, String role, int totalWorkingDays, int totalAttendanceRecords,
			Double attendancePercentage, Double totalHoursPresent, Double averageCheckInTime, long presentCount,
			long absentCount, long lateCount, long approvedLeaveCount, long pendingLeaveCount, long totalUsers,
			Map<String, Double> departmentAttendanceBreakdown, Map<String, Double> topDefaulters) {
		super();
		this.userName = userName;
		this.role = role;
		this.totalWorkingDays = totalWorkingDays;
		this.totalAttendanceRecords = totalAttendanceRecords;
		this.attendancePercentage = attendancePercentage;
		this.totalHoursPresent = totalHoursPresent;
		this.averageCheckInTime = averageCheckInTime;
		this.presentCount = presentCount;
		this.absentCount = absentCount;
		this.lateCount = lateCount;
		this.approvedLeaveCount = approvedLeaveCount;
		this.pendingLeaveCount = pendingLeaveCount;
		this.totalUsers = totalUsers;
		this.departmentAttendanceBreakdown = departmentAttendanceBreakdown;
		this.topDefaulters = topDefaulters;
	}

	public DashboardStatsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DashboardStatsDTO [userName=" + userName + ", role=" + role + ", totalWorkingDays=" + totalWorkingDays
				+ ", totalAttendanceRecords=" + totalAttendanceRecords + ", attendancePercentage="
				+ attendancePercentage + ", totalHoursPresent=" + totalHoursPresent + ", averageCheckInTime="
				+ averageCheckInTime + ", presentCount=" + presentCount + ", absentCount=" + absentCount
				+ ", lateCount=" + lateCount + ", approvedLeaveCount=" + approvedLeaveCount + ", pendingLeaveCount="
				+ pendingLeaveCount + ", totalUsers=" + totalUsers + ", departmentAttendanceBreakdown="
				+ departmentAttendanceBreakdown + ", topDefaulters=" + topDefaulters + "]";
	} 
    
    
}

