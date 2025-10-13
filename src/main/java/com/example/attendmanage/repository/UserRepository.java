package com.example.attendmanage.repository;

import com.example.attendmanage.entity.User;
import com.example.attendmanage.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmail(String email);

    Optional<User> findByRole(UserRole role); 
    
    boolean existsByEmail(String email);
    
}
