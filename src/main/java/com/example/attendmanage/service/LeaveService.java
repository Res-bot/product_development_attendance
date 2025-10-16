package com.example.attendmanage.service;

import com.example.attendmanage.dto.LeaveRequestDTO;
import com.example.attendmanage.entity.LeaveRequest;
import com.example.attendmanage.entity.User;
import com.example.attendmanage.enums.LeaveStatus;
import com.example.attendmanage.repository.LeaveRepository;
import com.example.attendmanage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final UserRepository userRepository;

    private LeaveRequestDTO convertToDto(LeaveRequest request) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(request.getId());
        dto.setApplicantId(request.getApplicant().getId());
        dto.setApplicantName(request.getApplicant().getName());
        dto.setStartDate(request.getStartDate());
        dto.setEndDate(request.getEndDate());
        dto.setReason(request.getReason());
        dto.setStatus(request.getStatus());
        dto.setRequestedOn(request.getRequestedOn());
        
        if (request.getApprover() != null) {
            dto.setApproverId(request.getApprover().getId());
            dto.setApproverName(request.getApprover().getName());
            dto.setRejectionReason(request.getRejectionReason());
        }
        return dto;
    }

    public LeaveRequestDTO applyForLeave(Long applicantId, LeaveRequestDTO dto) {
    
        User applicant = userRepository.findById(applicantId)
            .orElseThrow(() -> new RuntimeException("Applicant user not found."));

        LeaveRequest request = new LeaveRequest();
        request.setApplicant(applicant);
        request.setStartDate(dto.getStartDate());
        request.setEndDate(dto.getEndDate());
        request.setReason(dto.getReason());
        request.setStatus(LeaveStatus.PENDING); 
        request.setRequestedOn(LocalDateTime.now());

        LeaveRequest savedRequest = leaveRepository.save(request);
        return convertToDto(savedRequest);
    }
    

    public List<LeaveRequestDTO> getAllPendingRequests() {
        return leaveRepository.findByStatus(LeaveStatus.PENDING).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    
    public LeaveRequestDTO updateLeaveStatus(Long requestId, Long approverId, LeaveStatus status, String rejectionReason) {
        LeaveRequest request = leaveRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Leave request not found."));
            
        User approver = userRepository.findById(approverId)
            .orElseThrow(() -> new RuntimeException("Approver user not found."));

        if (request.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Request is already processed.");
        }

        request.setStatus(status);
        request.setApprover(approver);
        request.setRejectionReason(status == LeaveStatus.REJECTED ? rejectionReason : null);

        LeaveRequest updatedRequest = leaveRepository.save(request);
        
        
        return convertToDto(updatedRequest);
    }
    
    
    public List<LeaveRequestDTO> getLeaveHistoryByApplicant(Long applicantId) {
        return leaveRepository.findByApplicant_IdOrderByRequestedOnDesc(applicantId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
}
