package com.inn.attendanceapi.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@NoArgsConstructor
public class SeanceParticipantWrapper {
    private UserWrapper user;
    private boolean isPresent;
    private Time entryTime;

    public SeanceParticipantWrapper(UserWrapper user, boolean isPresent) {
        this(user, isPresent, null);
    }

    public SeanceParticipantWrapper(UserWrapper user, boolean isPresent, Time entryTime) {
        this.user = user;
        this.isPresent = isPresent;
        this.entryTime = entryTime;
    }

    // getters and setters
    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }
}

