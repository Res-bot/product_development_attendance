package in.sp.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.entity.Attendance;

public interface Attendancerepo extends JpaRepository<Attendance, Long> {
	
	Optional<Attendance> findTopByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(Long userId);
	

}
