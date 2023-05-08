package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterDao extends JpaRepository<Semester,Integer> {
}
