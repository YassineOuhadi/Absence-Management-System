package com.inn.attendanceapi.record;

import java.sql.Time;

public record PresenceRecord(boolean isPresent, Time entryTime) {
    // You can add any additional methods or constructors here
}