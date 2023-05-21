package com.inn.attendanceapi.model.id;


import java.io.Serializable;
import java.util.Objects;

public class JustificationId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer seance;
    private Integer user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JustificationId)) return false;
        JustificationId that = (JustificationId) o;
        return Objects.equals(seance, that.seance) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seance,user);
    }
}
