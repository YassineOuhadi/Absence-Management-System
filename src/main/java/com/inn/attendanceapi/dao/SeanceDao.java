package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Salle;
import com.inn.attendanceapi.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeanceDao extends JpaRepository<Seance, Integer> {
    List<Seance> findAllSeancesBySalleAndDate(Salle salle, LocalDate date);
}



