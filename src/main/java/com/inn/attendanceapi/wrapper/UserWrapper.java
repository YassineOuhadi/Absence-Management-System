package com.inn.attendanceapi.wrapper;

import com.inn.attendanceapi.FactoryPattern.UserFactory;
import com.inn.attendanceapi.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String firstName;

    private String lastName;

    private String rfid;

    private String email;

    private String contactNumber;

    private String status;

    private UserFactory.UserRole role;

    public UserWrapper(Integer id, String firstName, String lastName, String rfid, String email, String contactNumber, String status, UserFactory.UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rfid = rfid;
        this.email = email;
        this.contactNumber = contactNumber;
        this.status = status;
        this.role = role;
    }

    public UserWrapper(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rfid = user.getRfid();
        this.email = user.getEmail();
        this.contactNumber = user.getContactNumber();
        this.status = user.getStatus();
        this.role = user.getRole();
    }

}