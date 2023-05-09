package com.inn.attendanceapi.model;

import com.inn.attendanceapi.model.id.SemesterBranchModuleId;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@IdClass(SemesterBranchModuleId.class)
@Table(name = "semesterBranchModules", uniqueConstraints = @UniqueConstraint(columnNames = {"semester_id", "branch_id", "module_id", "level"}))
@NamedQueries({
        @NamedQuery(
                name = "SemesterBranchModules.findBySemesterIdAndBranchIdAndLevel",
                query = "SELECT sbm FROM SemesterBranchModules sbm WHERE sbm.semester.id = :semester_id AND sbm.branch.id = :branch_id AND sbm.level = :level"
        )
})
public class SemesterBranchModules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private YearBranchStudents.Level level;
}
