package com.ams.attendance.dto;

public class AuthResponse {
    private String jwtToken;
    private String email;
    private String role;
    private Long userId;
    private String userName;

    // No-args constructor
    public AuthResponse() {
    }

    // All-args constructor
    public AuthResponse(String jwtToken, String email, String role, Long userId, String userName) {
        this.jwtToken = jwtToken;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "AuthResponse [jwtToken=" + jwtToken + ", email=" + email + ", role=" + role + ", userId=" + userId
                + ", userName=" + userName + "]";
    }
}
