package com.ams.attendance.dto;


import com.ams.attendance.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    
    @NotEmpty(message = "Name is required")
    private String name;
    
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    // Password is only required for register/login requests
    private String password; 
    private UserRole role;
    private String department;
    private String designation;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public UserDTO(Long id, @NotEmpty(message = "Name is required") String name,
			@Email(message = "Email should be valid") @NotEmpty(message = "Email is required") String email,
			String password, UserRole role, String department, String designation) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.department = department;
		this.designation = designation;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", department=" + department + ", designation=" + designation + "]";
	}
    
    
}