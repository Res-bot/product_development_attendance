package com.ams.attendance.repository;

import com.ams.attendance.entity.User;
import com.ams.attendance.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

    // Initial check for Admin user setup
    Optional<User> findByRole(UserRole role); 
    
    // Check if email already exists during registration
    boolean existsByEmail(String email);
}
