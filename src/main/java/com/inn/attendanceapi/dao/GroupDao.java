package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDao extends JpaRepository<Group,Integer> {
}
