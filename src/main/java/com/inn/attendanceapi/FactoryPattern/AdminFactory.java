package com.inn.attendanceapi.FactoryPattern;

import com.inn.attendanceapi.model.User;

public class AdminFactory extends UserFactory {
    @Override
    public User createUser() {
        User admin = new User();
        admin.setRole(UserRole.ADMIN);
        return admin;
    }
}
