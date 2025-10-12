package com.ams.attendance.enums;

public enum AttendanceStatus {
    // For self check-in/out
    PRESENT,
    ABSENT,
    LATE,
    
    // For general status or reporting
    HALF_DAY,
    ON_LEAVE // A record can be marked as ON_LEAVE if a request was approved
}
