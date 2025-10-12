package com.ams.attendance.entity;

import com.ams.attendance.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Will store the BCrypt hash

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private String department;
    private String designation;
    private String phoneNumber;
    private String photoUrl; // For profile photo

    private LocalDateTime createdAt = LocalDateTime.now();


    @ManyToMany
    @JoinTable(
        name = "user_course",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();
    
    // --- Spring Security UserDetails implementation ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setDepartment(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(String encode) {
		// TODO Auto-generated method stub
		
	}

	public void setRole(UserRole admin) {
		// TODO Auto-generated method stub
		
	}
}
