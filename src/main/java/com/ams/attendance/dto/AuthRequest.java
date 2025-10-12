package com.ams.attendance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

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

	public AuthRequest(@Email(message = "Email should be valid") @NotEmpty(message = "Email is required") String email,
			@NotEmpty(message = "Password is required") String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public AuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AuthRequest [email=" + email + ", password=" + password + "]";
	}
    
    
}
