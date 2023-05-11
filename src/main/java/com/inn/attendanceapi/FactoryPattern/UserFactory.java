package com.inn.attendanceapi.FactoryPattern;

import com.inn.attendanceapi.model.User;

public abstract class UserFactory {

    public enum UserRole {
        ADMIN, STUDENT, PROFESSOR
    }
    public abstract User createUser();

    // Other common factory methods or logic can be defined here
}