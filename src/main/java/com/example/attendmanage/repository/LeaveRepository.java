package com.example.attendmanage.repository;

import com.example.attendmanage.entity.LeaveRequest;
import com.example.attendmanage.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

    
    List<LeaveRequest> findByStatus(LeaveStatus status);

    List<LeaveRequest> findByApplicant_IdOrderByRequestedOnDesc(Long applicantId);

    long countByApplicant_IdAndStatus(Long applicantId, LeaveStatus status);
}
