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
@Table(name = "year")
public class Year implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL)
    private Set<YearBranchStudents> yearBranchStudents = new HashSet<>();

    public String getAcademicYear() {
        return startYear + "-" + endYear;
    }
}
