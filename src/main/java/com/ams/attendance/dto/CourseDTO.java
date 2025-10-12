package com.ams.attendance.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String courseCode;
    private String name;
    private Integer credits;
    private Long departmentId; // Used for linking the course to a department
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public CourseDTO(Long id, String courseCode, String name, Integer credits, Long departmentId) {
		super();
		this.id = id;
		this.courseCode = courseCode;
		this.name = name;
		this.credits = credits;
		this.departmentId = departmentId;
	}
	public CourseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CourseDTO [id=" + id + ", courseCode=" + courseCode + ", name=" + name + ", credits=" + credits
				+ ", departmentId=" + departmentId + "]";
	}
    
    
}
