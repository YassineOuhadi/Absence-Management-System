package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeanceDao extends JpaRepository<Seance,Integer> {
}
