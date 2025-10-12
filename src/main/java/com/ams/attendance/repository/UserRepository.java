package com.ams.attendance.repository;

import com.ams.attendance.entity.User;
import com.ams.attendance.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

    // Initial check for Admin user setup
    Optional<User> findByRole(UserRole role); 
    
    // Check if email already exists during registration
    boolean existsByEmail(String email);
}
