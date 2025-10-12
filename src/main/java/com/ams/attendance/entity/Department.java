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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Department(Long id, String name, String description, List<User> users, List<Course> courses) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
		this.courses = courses;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", description=" + description + ", users=" + users
				+ ", courses=" + courses + "]";
	}
    
    
}
