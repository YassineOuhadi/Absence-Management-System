package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Presence;
import com.inn.attendanceapi.model.Seance;
import com.inn.attendanceapi.model.SeanceParticipants;
import com.inn.attendanceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresenceDao extends JpaRepository<Presence,Integer> {

    Presence findByUserAndSeance(User user, Seance seance);

    List<Presence> findBySeance(@Param("seance") Seance seance);

    List<Seance> findUnvalidatedSeances();

    boolean existsByValidate(@Param("isValidate") boolean isValidate);
}
