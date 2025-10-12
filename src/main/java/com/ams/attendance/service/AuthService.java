package com.ams.attendance.service;

import com.ams.attendance.dto.AuthRequest;
import com.ams.attendance.dto.AuthResponse;
import com.ams.attendance.dto.UserDTO;
import com.ams.attendance.entity.User;
import com.ams.attendance.enums.UserRole;
import com.ams.attendance.repository.UserRepository;
import com.ams.attendance.util.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthService {

    private  UserRepository userRepository;
    // Removed 'private final PasswordEncoder passwordEncoder;' as it is not directly used here
    private AuthenticationManager authenticationManager;
    private  JwtUtil jwtUtil;
    private AdminService adminService; // For user creation

    // --- User Registration (Encrypts password by delegating to AdminService) ---
    /**
     * Handles user registration, delegates creation and password hashing to AdminService.
     * @param userDto User details including plain text password.
     * @return UserDTO of the newly created user.
     */
    public UserDTO registerUser(UserDTO userDto) {
        // 1. Ensure email is unique
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("User with this email already exists.");
        }
        
        // 2. Default role determination
        UserRole role = userDto.getRole() != null ? userDto.getRole() : determineDefaultRole(userDto.getEmail());
        userDto.setRole(role);

        // 3. Delegation: AdminService.createUser handles the password encoding (BCrypt).
        return adminService.createUser(userDto);
    }
    
    // Simple logic to set a default role based on email or context
    private UserRole determineDefaultRole(String email) {
        if (email.endsWith("@student.com")) return UserRole.STUDENT;
        if (email.endsWith("@teacher.com")) return UserRole.TEACHER;
        return UserRole.EMPLOYEE;
    }


    // --- User Login (Authenticates credentials and issues JWT) ---
    /**
     * Authenticates the user and generates a JWT token upon successful login.
     * The password comparison (hashing) is handled internally by Spring Security.
     * @param authRequest Contains email (username) and plain text password.
     * @return AuthResponse containing the JWT token and user details.
     */
    public AuthResponse login(AuthRequest authRequest) {
        try {
            // 1. Authentication: The AuthenticationManager finds the User (via AdminService), 
            //    loads the hashed password, and compares it to the plain password provided.
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            
            // 2. Load User Details: Cast to our custom User entity to access ID and Name.
            final User user = (User) authentication.getPrincipal(); 
            
            // 3. Generate JWT token
            final String jwt = jwtUtil.generateToken(user.getUsername());
            
            // 4. Extract role and build response
            String role = user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            
            return new AuthResponse(
                jwt,
                user.getEmail(),
                role,
                user.getId(), 
                user.getName()
            );

        } catch (Exception e) {
            // Catch AuthenticationException (wrapped in a generic Exception) and provide a generic failure message.
            throw new RuntimeException("Authentication failed: Invalid email or password.", e);
        }
    }
}
