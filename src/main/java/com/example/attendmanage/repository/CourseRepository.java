package com.example.attendmanage.repository;

import com.example.attendmanage.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long>{
    Optional<Course> findByCourseCode(String courseCode);
}
