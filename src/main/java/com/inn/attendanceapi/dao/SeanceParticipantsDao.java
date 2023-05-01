package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.SeanceParticipants;
import com.inn.attendanceapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeanceParticipantsDao extends JpaRepository<SeanceParticipants,Integer> {

    List<SeanceParticipants> findBySeanceId(@Param("seance_id") Integer seance_id);

    List<SeanceParticipants> findBySeanceIdAndUserRole(@Param("seance_id") Integer seance_id, @Param("userRole") User.UserRole userRole);
}
