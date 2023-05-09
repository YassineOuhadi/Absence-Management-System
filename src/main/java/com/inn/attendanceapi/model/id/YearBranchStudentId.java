package com.inn.attendanceapi.model.id;

import com.inn.attendanceapi.model.YearBranchStudents;

import java.io.Serializable;
import java.util.Objects;

public class YearBranchStudentId implements Serializable {

    private Integer student;
    private Integer branch;
    private Integer year;

    private YearBranchStudents.Level level;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YearBranchStudentId)) return false;
        YearBranchStudentId that = (YearBranchStudentId) o;
        return Objects.equals(student, that.student) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(year, that.year) &&
                level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, branch, year, level);
    }

}
