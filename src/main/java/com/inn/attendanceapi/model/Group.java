package com.inn.attendanceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "\"group\"")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private YearBranchStudents.Level level;

    @Column(name = "by_branch", columnDefinition = "boolean default false")
    private boolean byBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_fk", nullable = false)
    private Year year;

    @JsonIgnoreProperties("groups")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "groupStudents",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> students = new HashSet<>();

    public Branch getGroupBranch() {
        if (students.isEmpty()) {
            return null;
        }

        Branch branch = null;
        for (User student : students) {
            for (YearBranchStudents yearBranchStudent : student.getYearBranchStudents()) {
                if (yearBranchStudent.getYear().equals(year) && yearBranchStudent.getLevel().equals(level)) {
                    if (branch == null) {
                        branch = yearBranchStudent.getBranch();
                    } else if (!Objects.equals(yearBranchStudent.getBranch(), branch)) {
                        // if the students in the group belong to different branches, return null
                        return null;
                    }
                    break;
                }
            }
        }

        // if all the students in the group belong to the same branch, return that branch
        return branch;
    }


}
