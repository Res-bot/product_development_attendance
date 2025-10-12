package in.sp.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sp.main.entity.LeaveRequest;

public interface Leaverepo extends JpaRepository<LeaveRequest, Long> {

}
