package com.example.attendmanage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.attendmanage.entity.Department;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

    Optional<Department> findByName(String name);    
}



