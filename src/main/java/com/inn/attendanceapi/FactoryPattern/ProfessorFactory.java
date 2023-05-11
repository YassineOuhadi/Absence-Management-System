package com.inn.attendanceapi.FactoryPattern;

import com.inn.attendanceapi.model.User;

public class ProfessorFactory extends UserFactory {
    @Override
    public User createUser() {
        User professor = new User();
        professor.setRole(UserRole.PROFESSOR);
        return professor;
    }
}
