package com.ams.attendance.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    // Relationship: One Department can have Many Users
    // MappedBy indicates that the 'department' field in the User entity owns the relationship.
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<User> users; 
    
    // Relationship: One Department can offer Many Courses
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Course> courses;
}
