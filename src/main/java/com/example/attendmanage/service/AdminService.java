package com.example.attendmanage.service;

import com.example.attendmanage.dto.UserDTO;
import com.example.attendmanage.dto.DepartmentDTO;
import com.example.attendmanage.dto.CourseDTO;
import com.example.attendmanage.entity.Course;
import com.example.attendmanage.entity.Department;
import com.example.attendmanage.entity.User;
import com.example.attendmanage.enums.UserRole;
import com.example.attendmanage.repository.CourseRepository;
import com.example.attendmanage.repository.DepartmentRepository;
import com.example.attendmanage.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AdminService implements UserDetailsService{

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository; 
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    @PostConstruct
    private void createAdminUser() {
        Optional<User> optionalUser = userRepository.findByRole(UserRole.ADMIN);
        if (optionalUser.isEmpty()) {
            User admin = new User();
            admin.setName("Default Admin");
            admin.setEmail("admin@ams.com");
            admin.setPassword(passwordEncoder.encode("admin123")); 
            admin.setRole(UserRole.ADMIN);
            admin.setDepartment("IT"); 
            userRepository.save(admin);
            System.out.println("Default Admin User created!");
        } else {
            System.out.println("Admin User Already exists.");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
    
    
    // Create User (Registration/Admin Add User)
    public UserDTO createUser(UserDTO userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("User with this email already exists.");
        }
        
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); 
        user.setRole(userDto.getRole() != null ? userDto.getRole() : UserRole.EMPLOYEE); 
        user.setDepartment(userDto.getDepartment());
        user.setDesignation(userDto.getDesignation());
        
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }
    
    // Get User by ID
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        return convertToDto(user);
    }
    
    // Update User
    public UserDTO updateUser(Long userId, UserDTO userDto) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getDepartment() != null) existingUser.setDepartment(userDto.getDepartment());
        if (userDto.getDesignation() != null) existingUser.setDesignation(userDto.getDesignation());
        
        if (userDto.getRole() != null) {
            existingUser.setRole(userDto.getRole());
        }
        
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }
    
    // Delete User
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }


    // Get All Users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }
    
    // Department Management (Create)
    private DepartmentDTO convertToDto(Department department) {
    DepartmentDTO dto = new DepartmentDTO();
    dto.setId(department.getId());
    dto.setName(department.getName());
    dto.setDescription(department.getDescription());
    return dto;
}

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        if (departmentRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("Department already exists: " + dto.getName());
        }
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        Department savedDept = departmentRepository.save(department);
        return convertToDto(savedDept);
    }
    
    // Course Management (Create)
    private CourseDTO convertToDto(Course course) {
    CourseDTO dto = new CourseDTO();
    dto.setId(course.getId());
    dto.setName(course.getName());
    dto.setCourseCode(course.getCourseCode());
    dto.setCredits(course.getCredits());
    // Only set department ID if the course is linked to one
    if (course.getDepartment() != null) {
        dto.setDepartmentId(course.getDepartment().getId());
    }
    return dto;
}

    public CourseDTO createCourse(CourseDTO dto) {
        if (courseRepository.findByCourseCode(dto.getCourseCode()).isPresent()) {
            throw new RuntimeException("Course with this code already exists: " + dto.getCourseCode());
        }
        
        Department department = departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Department not found."));

        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setName(dto.getName());
        course.setCredits(dto.getCredits());
        course.setDepartment(department);
        
        Course savedCourse = courseRepository.save(course);
        return convertToDto(savedCourse);
    }


     private UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setDepartment(user.getDepartment());
        dto.setDesignation(user.getDesignation());
        return dto;
    }


    
    
}
