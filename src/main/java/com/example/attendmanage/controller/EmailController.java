package com.example.attendmanage.controller;

import com.example.attendmanage.service.TeacherService;
import com.example.attendmanage.service.TeacherService.StudentEmailInfo;
import com.example.attendmanage.service.EmailService;


import lombok.RequiredArgsConstructor;
// import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/teacher/email")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')") 
public class EmailController {

    private final TeacherService teacherService;
    @Autowired
    private EmailService emailService;
    
    
    @PostMapping("/notify-absent/{courseId}")
    public ResponseEntity<String> notifyAbsentStudents(
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate) {

        List<StudentEmailInfo> absentStudents = teacherService.getAbsentStudentEmailInfo(courseId, attendanceDate);
        
        if (absentStudents.isEmpty()) {
             return ResponseEntity.ok("No absent students found to notify for Course " 
                                     + courseId + " on " + attendanceDate.toString());
        }

        for (StudentEmailInfo info : absentStudents) {
            emailService.sendEmail(info.getEmail(), info.getSubject(), info.getBody());
        }
        
        return ResponseEntity.ok(String.format(
            "Absence notification sent to %d students for Course %d on %s.", 
            absentStudents.size(), 
            courseId, 
            attendanceDate.toString()
        ));
    }


    @RequestMapping("/sendmsg")
    public String send(){
        emailService.sendEmail("reshmamahnaty53@gmail.com","Hello test","tryrtgftrdftfgytyy rtrdrttf  ");
        return "Mail send successfully";

    }
}