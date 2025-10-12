package in.sp.main.service;

import in.sp.main.dto.Attdto;
import in.sp.main.entity.Attendance;
import in.sp.main.repository.Attendancerepo;
import in.sp.main.repository.Userrepo;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class Employeeservice {

    private final Attendancerepo attendanceRepository;
    private final Userrepo userRepository;
    
    // Use Constructor Injection (best practice)
    public Employeeservice(Attendancerepo attendanceRepository, Userrepo userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }
    
    public Attdto checkIn(Long userId, LocalDateTime time, String notes) {
       
        // 2. Validation: Check if the user is already checked in
        Optional<Attendance> activeAttendance = 
            attendanceRepository.findTopByUserIdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(userId);

        if (activeAttendance.isPresent()) {
            throw new IllegalStateException("Employee is already checked in.");
        }
        
        // 3. Create the new Attendance record
        Attendance newAttendance = new Attendance();
        newAttendance.setUserId(userId);
        newAttendance.setCheckInTime(time);
        newAttendance.setCheckInNotes(notes);
        
        // 4. Save and return DTO
        Attendance savedAttendance = attendanceRepository.save(newAttendance);
        return convertToDto(savedAttendance);
    }
    
    // Helper method to convert Entity to DTO 
    private Attdto convertToDto(Attendance attendance) {
        Attdto dto = new Attdto();
        dto.setId(attendance.getId());
        dto.setUserId(attendance.getUserId());
        dto.setCheckInTime(attendance.getCheckInTime());
        dto.setCheckOutTime(attendance.getCheckOutTime());
        dto.setCheckInNotes(attendance.getCheckInNotes());
        return dto;
    }

}