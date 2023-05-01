package com.inn.attendanceapi.model;

import com.inn.attendanceapi.model.id.YearBranchStudentId;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@IdClass(YearBranchStudentId.class)
@Table(name = "yearBranchStudents", uniqueConstraints = @UniqueConstraint(columnNames = {"year_id", "branch_id", "student_id", "level"}))
@NamedQueries({
        @NamedQuery(
                name = "YearBranchStudents.findByYearIdAndBranchIdAndLevel",
                query = "SELECT ybs FROM YearBranchStudents ybs WHERE ybs.year.id = :year_id AND ybs.branch.id = :branch_id AND ybs.level = :level"
        )
})
public class YearBranchStudents implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Level {
        FIRST_YEAR,
        SECOND_YEAR,
        THIRD_YEAR
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id")
    private Year year;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private YearBranchStudents.Level level;

}
