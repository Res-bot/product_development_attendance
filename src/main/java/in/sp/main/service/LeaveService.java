package in.sp.main.service;


import in.sp.main.dto.Leavereqdto;
import in.sp.main.entity.LeaveRequest;
import in.sp.main.repository.Leaverepo;
import in.sp.main.repository.Userrepo;
import org.springframework.stereotype.Service;

@Service
public class LeaveService {

    private final Leaverepo leaveRepository;
    private final Userrepo userRepository;
    
    // Constructor Injection
    public LeaveService(Leaverepo leaveRepository, Userrepo userRepository) {
        this.leaveRepository = leaveRepository;
        this.userRepository = userRepository;
    }

    /**
     * Submits a new leave request with PENDING status.
     * @param userId The ID of the employee applying for leave.
     * @param request The LeaveRequestDTO containing dates and reason.
     * @return The created LeaveRequestDTO with the assigned ID and PENDING status.
     */
    public Leavereqdto applyForLeave(Long userId, Leavereqdto request) {
       

        if (request.getStartDate() == null || request.getEndDate() == null || request.getReason() == null) {
            throw new IllegalArgumentException("Start date, end date, and reason must be provided.");
        }
        
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }

        // 2. Create Entity
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserId(userId);
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason());
        // Status is automatically set to PENDING by the entity default constructor
        // or by explicitly setting it: leaveRequest.setStatus(LeaveStatus.PENDING);
        
        // 3. Save
        LeaveRequest savedRequest = leaveRepository.save(leaveRequest);
        
        // 4. Return DTO
        return convertToDto(savedRequest);
    }
    
    // Helper method to convert Entity to DTO
    private Leavereqdto convertToDto(LeaveRequest entity) {
    	Leavereqdto dto = new Leavereqdto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}