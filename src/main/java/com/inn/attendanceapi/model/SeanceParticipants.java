package com.inn.attendanceapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inn.attendanceapi.model.id.SeanceParticipantsId;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "seanceParticipants")
@IdClass(SeanceParticipantsId.class)
@NamedQueries({
        @NamedQuery(
                name = "SeanceParticipants.findBySeanceId",
                query = "SELECT sp FROM SeanceParticipants sp WHERE sp.seance.id = :seance_id"
        ),
        @NamedQuery(
                name = "SeanceParticipants.findBySeanceAndParticipant",
                query = "SELECT sp FROM SeanceParticipants sp WHERE sp.seance = :seance AND sp.user = :participant"
        ),
        @NamedQuery(
                name = "SeanceParticipants.findBySeanceIdAndUserRole",
                query = "SELECT sp.user FROM SeanceParticipants sp WHERE sp.seance.id = :seance_id AND sp.user.role = :role"
        )
})
public class SeanceParticipants implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seance_fk")
    private Seance seance;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk")
    private User user;

    @Column(name = "is_presence", columnDefinition = "boolean default false")
    private boolean isPresence;

}

