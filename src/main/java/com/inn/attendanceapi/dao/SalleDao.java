package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleDao extends JpaRepository<Salle,Integer> {
}
