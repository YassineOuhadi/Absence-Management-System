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
    private Time entryTime;



    public SeanceParticipantWrapper(UserWrapper user, boolean isPresent, Time entryTime) {
        this.user = user;
        this.isPresent = isPresent;
        this.entryTime = entryTime;
    }

    // getters and setters

}



