package com.inn.attendanceapi.FactoryPattern;

import com.inn.attendanceapi.model.User;

public class StudentFactory extends UserFactory {
    @Override
    public User createUser() {
        User student = new User();
        student.setRole(UserRole.STUDENT);
        return student;
    }
}
