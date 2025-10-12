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
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getRole() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getDepartment() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getDesignation() {
		// TODO Auto-generated method stub
		return null;
	}
	public CharSequence getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}