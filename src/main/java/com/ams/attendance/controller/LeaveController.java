package com.ams.attendance.controller;


import com.ams.attendance.dto.LeaveRequestDTO;
import com.ams.attendance.enums.LeaveStatus;
import com.ams.attendance.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService = new LeaveService();

    /**
     * POST /api/leaves/{applicantId}
     * Allows a user to submit a new leave request. (Feature 5)
     * The applicantId should typically come from the authenticated user's principal,
     * but is passed as a path variable here to match the service method signature.
     */
    @PostMapping("/{applicantId}")
    public ResponseEntity<LeaveRequestDTO> applyForLeave(
            @PathVariable Long applicantId,
            @RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            LeaveRequestDTO newRequest = leaveService.applyForLeave(applicantId, leaveRequestDTO);
            return new ResponseEntity<>(newRequest, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle exceptions like "Applicant user not found"
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /api/leaves/history/{applicantId}
     * Retrieves a user's own leave history. (Feature 5)
     * The applicantId should typically come from the authenticated user's principal.
     */
    @GetMapping("/history/{applicantId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveHistory(@PathVariable Long applicantId) {
        List<LeaveRequestDTO> history = leaveService.getLeaveHistoryByApplicant(applicantId);
        return ResponseEntity.ok(history);
    }

    /**
     * GET /api/leaves/pending
     * Retrieves all PENDING leave requests for Admin/Teacher review. (Feature 5)
     * NOTE: This endpoint should be secured to only allow users with APPROVER roles (e.g., Admin, Teacher).
     */
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequestDTO>> getAllPendingRequests() {
        List<LeaveRequestDTO> pendingRequests = leaveService.getAllPendingRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    // DTO for update status request body
    public static class UpdateStatusRequest {
        private Long approverId;
        private LeaveStatus status;
        private String rejectionReason;

        // Getters and Setters (omitted for brevity but required by Spring/Jackson)
        public Long getApproverId() { return approverId; }
        public void setApproverId(Long approverId) { this.approverId = approverId; }
        public LeaveStatus getStatus() { return status; }
        public void setStatus(LeaveStatus status) { this.status = status; }
        public String getRejectionReason() { return rejectionReason; }
        public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    }

    /**
     * PUT /api/leaves/{requestId}/status
     * Approves or rejects a leave request. (Feature 5)
     * NOTE: This endpoint should be secured to only allow users with APPROVER roles.
     */
    @PutMapping("/{requestId}/status")
    public ResponseEntity<LeaveRequestDTO> updateLeaveStatus(
            @PathVariable Long requestId,
            @RequestBody UpdateStatusRequest requestBody) {
        try {
            if (requestBody.getStatus() == null || requestBody.getApproverId() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            LeaveRequestDTO updatedRequest = leaveService.updateLeaveStatus(
                    requestId,
                    requestBody.getApproverId(),
                    requestBody.getStatus(),
                    requestBody.getRejectionReason());

            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException e) {
            // Handle exceptions like "Leave request not found", "Approver user not found", "Request is already processed"
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Or BAD_REQUEST depending on the error
        }
    }
}
