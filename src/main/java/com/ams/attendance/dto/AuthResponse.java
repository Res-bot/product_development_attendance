package com.ams.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {
    private String jwtToken;
    private String email;
    private String role;
    
    // Optional: User ID for frontend convenience
    private Long userId; 
    
    // Optional: User name for display
    private String userName; 
}
