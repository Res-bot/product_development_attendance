package com.ams.attendance.repository;

import com.ams.attendance.entity.LeaveRequest;
import com.ams.attendance.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {

    /**
     * Finds all leave requests matching a specific status (e.g., PENDING).
     * Used by getAllPendingRequests in LeaveService.
     */
    List<LeaveRequest> findByStatus(LeaveStatus status);

    /**
     * Retrieves all leave requests submitted by a specific user, sorted newest first.
     * Used by getLeaveHistoryByApplicant in LeaveService.
     * * The naming convention `Applicant_Id` refers to the `applicant` field in the 
     * LeaveRequest entity, followed by the nested `Id` field of the User entity.
     */
    List<LeaveRequest> findByApplicant_IdOrderByRequestedOnDesc(Long applicantId);

     // --- NEW METHOD for Dashboard/Statistics ---
    /**
     * Counts the number of leave requests for an applicant with a specific status.
     * This method supports both the APPROVED and PENDING counts used in StudentService.
     * * Signature matches: leaveRepository.countByApplicant_IdAndStatus(userId, LeaveStatus.X)
     */
    long countByApplicant_IdAndStatus(Long applicantId, LeaveStatus status);
}
