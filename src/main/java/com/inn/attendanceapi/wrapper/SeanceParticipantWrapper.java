package com.inn.attendanceapi.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class SeanceParticipantWrapper {
    private UserWrapper user;
    private boolean isPresent;
    private boolean isValidate;
    private boolean isJustified;
    private Time entryTime;



    public SeanceParticipantWrapper(UserWrapper user, boolean isPresent, boolean isValidate, boolean isJustified, Time entryTime) {
        this.user = user;
        this.isPresent = isPresent;
        this.isValidate = isValidate;
        this.isJustified = isJustified;
        this.entryTime = entryTime;
    }

    // getters and setters

}



