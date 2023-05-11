package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Justification;
import com.inn.attendanceapi.model.Seance;
import com.inn.attendanceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JustificationDao extends JpaRepository<Justification,Integer> {

    Optional<Justification> findByParticipantAndSeance(User participant, Seance seance);
}
