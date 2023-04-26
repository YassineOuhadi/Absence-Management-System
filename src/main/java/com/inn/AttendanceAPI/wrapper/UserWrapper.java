package com.inn.AttendanceApi.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String status;

    public UserWrapper(Integer id, String firstName, String lastName, String email, String contactNumber, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.status = status;
    }

}