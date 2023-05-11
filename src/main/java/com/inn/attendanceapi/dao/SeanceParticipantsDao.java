package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.FactoryPattern.UserFactory;
import com.inn.attendanceapi.model.Seance;
import com.inn.attendanceapi.model.SeanceParticipants;
import com.inn.attendanceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeanceParticipantsDao extends JpaRepository<SeanceParticipants,Integer> {

    List<SeanceParticipants> findBySeanceId(@Param("seance_id") Integer seance_id);

    Optional<SeanceParticipants> findBySeanceAndParticipant(@Param("seance")Seance seance,@Param("participant") User participant);

    User findBySeanceIdAndUserRole(@Param("seance_id") Integer seance_id, @Param("role") UserFactory.UserRole userRole);
}
