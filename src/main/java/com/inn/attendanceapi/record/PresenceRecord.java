package com.inn.attendanceapi.record;

import java.sql.Time;

public record PresenceRecord(boolean isPresent, boolean isValidate, boolean isJustified, Time entryTime) {
    //
}