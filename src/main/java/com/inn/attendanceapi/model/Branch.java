package com.inn.attendanceapi.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "branch")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<YearBranchStudents> yearBranchStudents = new HashSet<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<SemesterBranchModules> semesterBranchModules = new HashSet<>();
}
