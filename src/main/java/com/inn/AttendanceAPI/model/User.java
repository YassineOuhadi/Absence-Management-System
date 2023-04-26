package com.inn.AttendanceAPI.model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "SELECT u FROM User u WHERE u.email = :email")

@NamedQuery(name = "User.getAllUsers", query = "SELECT new com.inn.AttendanceApi.wrapper.UserWrapper(u.id,u.firstName,u.lastName,u.email,u.contactNumber,u.status) from User u WHERE u.role = 'user'")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.email from User u WHERE u.role = 'admin'")

@NamedQuery(name = "User.updateStatus", query = "UPDATE User u set u.status= :status WHERE u.id = :id")

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

    @Column(name = "email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
}
