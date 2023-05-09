package com.inn.attendanceapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<SemesterBranchModules> semesterBranchModules = new HashSet<>();
}
