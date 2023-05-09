package com.inn.attendanceapi.model.id;

import com.inn.attendanceapi.model.YearBranchStudents;

import java.io.Serializable;
import java.util.Objects;

public class SemesterBranchModuleId implements Serializable {

    private Integer semester;
    private Integer branch;
    private Integer module;

    private YearBranchStudents.Level level;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemesterBranchModuleId)) return false;
        SemesterBranchModuleId that = (SemesterBranchModuleId) o;
        return Objects.equals(semester, that.semester) &&
                Objects.equals(branch, that.branch) &&
                Objects.equals(module, that.module) &&
                level == that.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(semester, branch, module, level);
    }
}
