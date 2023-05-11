package com.inn.attendanceapi.model;

import com.inn.attendanceapi.FactoryPattern.UserFactory;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NamedQuery(name = "User.findByEmailId", query = "SELECT u FROM User u WHERE u.email = :email")

@NamedQuery(name = "User.getAllStudents", query = "SELECT new com.inn.attendanceapi.wrapper.UserWrapper(u.id,u.firstName,u.lastName,u.rfid,u.email,u.contactNumber,u.status,u.role) from User u WHERE u.role = 'STUDENT'")

@NamedQuery(name = "User.getAllProfessors", query = "SELECT new com.inn.attendanceapi.wrapper.UserWrapper(u.id,u.firstName,u.lastName,u.rfid,u.email,u.contactNumber,u.status,u.role) from User u WHERE u.role = 'PROFESSOR'")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.email from User u WHERE u.role = 'ADMIN'")

@NamedQuery(name = "User.updateStatus", query = "UPDATE User u set u.status= :status WHERE u.id = :id")

@NamedQuery(name = "User.findByGroupId", query = "SELECT u FROM User u JOIN u.groups g WHERE g.id = :groupId")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "\"user\"")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "rfid", nullable = true)
    private String rfid;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserFactory.UserRole role;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<YearBranchStudents> yearBranchStudents = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    private Set<Group> groups = new HashSet<>();
}
